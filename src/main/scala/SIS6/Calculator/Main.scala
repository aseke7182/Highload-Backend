package SIS6.Calculator

import akka.actor.typed.ActorSystem

object Main extends App {
  val system: ActorSystem[CalculatorMain.StartCalculate] = ActorSystem(CalculatorMain(), "calculator")
  while(true){
    val input = scala.io.StdIn.readLine()
    system ! CalculatorMain.StartCalculate(input)
  }
}
