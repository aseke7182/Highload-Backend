import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.{Behaviors, Routers}
import com.typesafe.config.{Config, ConfigFactory}
import api.{NodeRouter, Server}
import node.Node
import org.slf4j.{Logger, LoggerFactory}

object Main {

  val config: Config = ConfigFactory.load()
  val address = config.getString("http.ip")
  val port = config.getInt("http.port")
  val nodeId = config.getString("clustering.ip")

  def main(args: Array[String]): Unit = {

    implicit val log: Logger = LoggerFactory.getLogger(getClass)

    val rootBehavior = Behaviors.setup[Node.Command] { context =>
      context.spawnAnonymous(Node(nodeId))
      val group = Routers.group(Node.NodeServiceKey).withRoundRobinRouting()
      val node = context.spawnAnonymous(group)
      val router = new NodeRouter(node)(context.system, context.executionContext)
      Server.startHttpServer(router.route, address, port)(context.system, context.executionContext)
      Behaviors.empty
    }
    ActorSystem[Node.Command](rootBehavior, "cluster-playground")
  }
}