package SIS6.Calculator

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import Week2_Calculator.Calculation

object CalculatorLogic {

  def apply(): Behavior[CalculatorConductor.Calculated] = {
    Behaviors.receive { (context, message) =>
      val result = new Calculation start (message.whom)

      result match {
        case Left(x) => context.log.info("Something Strange: {}", x)
        case Right(x) => context.log.info("Answer: {}", x)
      }
      Behaviors.same
    }
  }

}
