import AddressBookRepository.{AddressBookNotFound, EmptyNameField}
import akka.http.scaladsl.server.{Directive1, Directives}

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait AddressBookDirectives extends Directives {

  def handle[T](f: Future[T])(errorHandler: Throwable => ApiError): Directive1[T] = onComplete(f) flatMap {
    case Success(t) =>
      provide(t)
    case Failure(error) =>
      val apiError = errorHandler(error)
      complete(apiError.statusCode, apiError.message)
  }

  def handleWithGeneric[T](f: Future[T]): Directive1[T] =
    handle[T](f)(handleError)

  private def handleError(e: Throwable): ApiError = e match {
    case EmptyNameField() => ApiError.emptyNameField
    case AddressBookNotFound(id) => ApiError.itemNotFound(id)
    case _ => ApiError.generic
  }
}
