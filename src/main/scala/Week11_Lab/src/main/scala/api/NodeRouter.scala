package api

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import node.Node
import node.Node.Pong

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

trait Router {
  def route: Route
}

class NodeRouter(node: ActorRef[Node.Command])(implicit system: ActorSystem[_], ex: ExecutionContext) extends Router with Directives {

  implicit val timeout: Timeout = 3.seconds

  override def route = {
    concat(
      processRoutes,
      healthRoute,
      statusRoutes
    )
  }

  lazy val healthRoute: Route = pathPrefix("health") {
    concat(
      pathEnd {
        concat(
          get {
            complete(StatusCodes.OK)
          }
        )
      }
    )
  }

  lazy val statusRoutes: Route = pathPrefix("status") {
    concat(
      pathPrefix("members") {
        concat(
          pathEnd {
            concat(
              get {
                val result = node.ask(ref=> Node.GetClusterMembers(ref))
                complete(result)
              }
            )
          }
        )
      }
    )
  }

  lazy val processRoutes: Route = pathPrefix("process") {
    concat(
      pathPrefix("fibonacci") {
        concat(
          path(IntNumber) { n =>
            pathEnd {
              concat(
                get {
                  val result = node.ask[Pong](ref => Node.FindNumberPing(n, ref))
                  complete(result)
                }
              )
            }
          }
        )
      }
    )
  }
}




