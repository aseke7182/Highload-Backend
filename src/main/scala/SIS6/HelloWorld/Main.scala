package SIS6.HelloWorld

import akka.actor.typed.ActorSystem

object Main extends App {
  val system: ActorSystem[HelloWorldMain.SayHello] = ActorSystem(HelloWorldMain(), "hello")

  system ! HelloWorldMain.SayHello("World")
}
