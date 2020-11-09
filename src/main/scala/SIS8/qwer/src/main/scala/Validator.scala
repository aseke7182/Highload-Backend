trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object CreateAddressBookValidator extends Validator[CreateAddressBook] {
  override def validate(createAddressBook: CreateAddressBook): Option[ApiError] =
    if (createAddressBook.name.isEmpty)
      Some(ApiError.emptyNameField)
    else
      None
}

object UpdateAddressBookValidator extends Validator[UpdateAddressBook] {
  override def validate(updateAddressBook: UpdateAddressBook): Option[ApiError] =
    if (updateAddressBook.name.exists(_.isEmpty))
      Some(ApiError.emptyNameField)
    else
      None
}
