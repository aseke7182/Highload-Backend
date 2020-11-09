package SIS6.Calculator

import akka.actor.typed.ActorSystem
import org.slf4j.{Logger, LoggerFactory}

object Main extends App {
  implicit val log: Logger = LoggerFactory.getLogger(getClass)
  val system: ActorSystem[CalculatorMain.StartCalculate] = ActorSystem(CalculatorMain(), "calculator")
  log.info("Calculator app has started")
  while(true){
    val input = scala.io.StdIn.readLine()
    system ! CalculatorMain.StartCalculate(input)
  }
}
