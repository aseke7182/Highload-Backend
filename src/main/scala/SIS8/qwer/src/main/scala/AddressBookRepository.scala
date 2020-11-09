import java.util.UUID

import AddressBookRepository.AddressBookNotFound

import scala.concurrent.{ExecutionContext, Future}

trait AddressBookRepository {
  def all(): Future[Seq[AddressBook]]

  def search(field: String): Future[Seq[AddressBook]]

  def create(creteAddressBook: CreateAddressBook): Future[AddressBook]

  def update(id: String, updateAddressBook: UpdateAddressBook): Future[AddressBook]

  def get(id: String): Future[AddressBook]

  def delete(id: String): Future[AddressBook]
}

object AddressBookRepository {

  final case class AddressBookNotFound(id: String) extends Exception(s"Address Book with $id not found.")
  final case class EmptyNameField() extends Exception()

}

class InMemoryAddressBookDB(addresses: Seq[AddressBook] = Seq.empty)(implicit ex: ExecutionContext) extends AddressBookRepository {
  private var addressBook: Seq[AddressBook] = addresses

  override def all(): Future[Seq[AddressBook]] = Future.successful(addressBook)

  override def create(createAddressBook: CreateAddressBook): Future[AddressBook] = {
    val newAddressBook = AddressBook(
      id = UUID.randomUUID().toString,
      name = createAddressBook.name,
      surname = createAddressBook.surname, address = createAddressBook.address, telephoneNumber = createAddressBook.telephoneNumber * 1L
    )
    addressBook :+= newAddressBook
    Future.successful(newAddressBook)
  }

  override def search(field: String): Future[Seq[AddressBook]] = {
    Future.successful(addressBook.filter(x => x.name.toLowerCase() == field.toLowerCase() || x.surname.toLowerCase() == field.toLowerCase()))
  }

  override def update(id: String, updateAddressBook: UpdateAddressBook): Future[AddressBook] = {
    addressBook.find(_.id == id) match {
      case Some(founded) =>
        val newAddressBook = updateHelper(founded, updateAddressBook)
        addressBook = addressBook.map(t => if (t.id == id) newAddressBook else t)
        Future.successful(newAddressBook)
      case None => Future.failed(AddressBookNotFound(id))
    }
  }

  override def get(id: String): Future[AddressBook] = {
    addressBook.find(_.id == id) match{
      case Some(founded) =>
        Future.successful(founded)
      case None => Future.failed(AddressBookNotFound(id))
    }
  }

  override def delete(id: String): Future[AddressBook] = {
    addressBook.find(_.id == id) match {
      case Some(founded) =>
        addressBook = addressBook.filterNot(_.id == id)
        Future.successful(founded)
      case None => Future.failed(AddressBookNotFound(id))
    }
  }

  private def updateHelper(addressBook: AddressBook, updateAddressBook: UpdateAddressBook): AddressBook = {
    val t1 = updateAddressBook.name.map(name => addressBook.copy(name = name)).getOrElse(addressBook)
    val t2 = updateAddressBook.surname.map(surname => t1.copy(surname = surname)).getOrElse(t1)
    val t3 = updateAddressBook.telephoneNumber.map(telephone => t2.copy(telephoneNumber = telephone)).getOrElse(t2)
    updateAddressBook.address.map(address => t3.copy(address = address)).getOrElse(t3)
  }
}
