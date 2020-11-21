package node

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import akka.util.Timeout
import service.FibonacciService
import node.cluster._

import scala.concurrent.duration.DurationInt

object Node {

  val NodeServiceKey = ServiceKey[Command]("node-service-key")

  sealed trait Command

  final case class Pong(nodeName: String, res: BigInt) extends Command

  final case class FindNumberPing(n: Int, replyTo: ActorRef[Pong]) extends Command

  final case class GetClusterMembers(replyTo: ActorRef[List[String]]) extends Command

  def apply(nodeId: String): Behavior[Command] = {
    Behaviors.setup[Command] { context =>

      implicit def system = context.system

      implicit def scheduler = context.system.scheduler

      implicit lazy val timeout = Timeout(5.seconds)

      context.system.receptionist ! Receptionist.Register(NodeServiceKey, context.self)
      val ps = context.spawnAnonymous(FibonacciService(nodeId))
      val cm = context.spawnAnonymous(ClusterManager("cm"))

      Behaviors.receiveMessage { message =>
        message match {
          case FindNumberPing(n, replyTo) => {
            ps ! FibonacciService.Ping(n, replyTo) //context.self)
            Behaviors.same
          }
          case Pong(nodeName, res) => {
            println(s"nodeName:${nodeName} result:${res}")
            Behaviors.same
          }
          case GetClusterMembers(replyTo) => {
            cm ! ClusterManager.GetMembers(replyTo)
            Behaviors.same
          }
        }
      }
    }
  }
}