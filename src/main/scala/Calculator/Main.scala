package Calculator
import Calculator.Calculation

object Main extends App {

  val line = scala.io.StdIn.readLine()
  val result = new Calculation start (line)
  println(result match {
    case Left(x) => x
    case Right(x) => s"Answer: $x"
  })

  // DID:
  // 1) Convert my input to arrays
  // 2) Overall logic
  // 3) Priority
  // 4) Checking for validness of input
  // 5) Either

  // TODO:
  // 1) Either: Done
  // 2) Priority: Done
  // 3) Calculation
}

