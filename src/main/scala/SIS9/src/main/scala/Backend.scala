import Service3.getClass
import org.slf4j.{Logger, LoggerFactory}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}
object Backend extends App {
  implicit val log: Logger = LoggerFactory.getLogger(getClass)

  val rootBehavior = Behaviors.setup[Nothing] { context =>
    val host = "localhost"
    val port = Try(System.getenv("PORT")).map(_.toInt).getOrElse(8080)
    MyServer.startHttpServer(Route1.route(context.executionContext), host, port)(context.system, context.executionContext)
    Behaviors.empty
  }
  val system = ActorSystem[Nothing](rootBehavior, "NumberService")
}
