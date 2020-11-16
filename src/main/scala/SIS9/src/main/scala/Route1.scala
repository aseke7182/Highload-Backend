import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, path}
import akka.http.scaladsl.server.{Directives, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
//import io.circe.generic.auto._

import scala.concurrent.ExecutionContext

object Route1 {

  private def healthcheck: Route = {
    path("ping") {
      Directives.get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
      }
    }
  }

  private def service3 : Route = {
    path("numbers") {
      Directives.get {
        complete(Service3.number())
      }
    }
  }

  def route(ex: ExecutionContext): Route = {
    Directives.concat(
      healthcheck,
      service3
    )
  }
}
