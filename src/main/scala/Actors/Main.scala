package Actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

object Main extends App {
  val supervisor: ActorSystem[Supervisor.SupervisorMessage] = ActorSystem(
    Supervisor(),
    "Supervisor"
  )

  // send the Start message to the Supervisor
  supervisor ! Supervisor.Start

  // wait a few moments, and then stop the Supervisor
  Thread.sleep(200)
  supervisor.terminate()
}
