package SIS6.Calculator

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object CalculatorMain {

  final case class StartCalculate(name: String)

  def apply(): Behavior[StartCalculate] =
    Behaviors.setup { context =>
      val greeter = context.spawn(CalculatorConductor(), "justPrintThatIStart")

      Behaviors.receiveMessage { message =>

        val replyTo = context.spawn(CalculatorLogic(), message.name)
        greeter ! CalculatorConductor.Calculate(message.name, replyTo)
        Behaviors.same
      }
    }

}
