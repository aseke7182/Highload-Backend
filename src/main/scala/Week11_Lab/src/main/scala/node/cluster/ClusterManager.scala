package node.cluster

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.cluster.typed.Cluster
import akka.cluster.MemberStatus

object ClusterManager {
  final case class GetMembers(replyTo: ActorRef[List[String]])

  def apply(nodeName: String) : Behavior[GetMembers] = Behaviors.setup{ context =>
    val cluster = Cluster(context.system)

    Behaviors.receiveMessage{
        case GetMembers(replyTo) => {
          replyTo ! cluster.state.members.filter(_.status == MemberStatus.up)
            .toList
            .map(_.address.toString)
          Behaviors.same
        }
      }
  }
}
