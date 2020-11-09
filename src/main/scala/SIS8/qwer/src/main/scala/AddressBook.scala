case class AddressBook(id: String, name: String, surname: String, address: String, telephoneNumber: BigInt)
case class CreateAddressBook(name: String, surname: String, address: String, telephoneNumber: BigInt)
case class UpdateAddressBook(name: Option[String], surname: Option[String], address: Option[String], telephoneNumber: Option[BigInt])