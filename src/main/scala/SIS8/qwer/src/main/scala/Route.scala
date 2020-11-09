import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import scala.concurrent.ExecutionContext

object Route extends ValidatorDirectives with AddressBookDirectives {

  private def healthcheck: Route = {
    path("ping") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
      }
    }
  }

  private def addressBook(ex: ExecutionContext): Route = {
    val addresses = Seq(
      AddressBook("1", "adel'", "zhang", "Pavlodar 75", 87878787545L),
      AddressBook("2", "askar", "mussayev", "Pavlodar 38", 87776565333L)
    )
    val addressBooksDB = new InMemoryAddressBookDB(addresses)(ex)
    val route = {
      concat(
        path("ping") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
          }
        },
        pathPrefix("addressBooks") {
          concat(
            get {
              concat(
                path("search" / Segment) { field =>
                  onSuccess(addressBooksDB.search(field)) { result =>
                    complete(result)
                  }
                },
                pathEnd {
                  complete(addressBooksDB.all())
                }
              )
            },
            post {
              entity(as[CreateAddressBook]) { createAddressBook =>
                validateWith(CreateAddressBookValidator)(createAddressBook) {
                  handleWithGeneric(addressBooksDB.create(createAddressBook)) { addressBookRes =>
                    complete(addressBookRes)
                  }
                }
              }
            },
            path(Segment) { id: String =>
              concat(
                put {
                  entity(as[UpdateAddressBook]) { updateAddressBook =>
                    validateWith(UpdateAddressBookValidator)(updateAddressBook) {
                      handle(addressBooksDB.update(id, updateAddressBook)) {
                        case AddressBookRepository.AddressBookNotFound(_) =>
                          ApiError.itemNotFound(id)
                        case _ =>
                          ApiError.generic
                      } { addressBook =>
                        complete(addressBook)
                      }
                    }
                  }
                },
                get {
                  handle(addressBooksDB.get(id)) {
                    case AddressBookRepository.AddressBookNotFound(_) =>
                      ApiError.itemNotFound(id)
                    case _ =>
                      ApiError.generic
                  }{ updatedAddressBook =>
                      complete(updatedAddressBook)
                  }
                },
                delete {
                  handle(addressBooksDB.delete(id)){
                    case AddressBookRepository.AddressBookNotFound(_) =>
                      ApiError.itemNotFound(id)
                    case _ =>
                      ApiError.generic
                  }{ deletedAddressBook =>
                    complete(deletedAddressBook)
                  }
                }
              )
            }
          )
        }
      )
    }
    route
  }

  def route(ex: ExecutionContext): Route = {
    concat(
      healthcheck,
      addressBook(ex)
    )
  }
}
