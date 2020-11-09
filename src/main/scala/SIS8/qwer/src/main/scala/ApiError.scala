import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {
  private def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

  val generic: ApiError = new ApiError(StatusCodes.InternalServerError, "Unknown error.")

  val emptyNameField: ApiError = new ApiError(StatusCodes.BadRequest, "The name field must not be empty.")

  def itemNotFound(id: String): ApiError =
    new ApiError(StatusCodes.NotFound, s"The address book item with id $id could not be found.")
}