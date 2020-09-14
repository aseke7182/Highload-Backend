package Calculator

import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}

object Main extends App {

  class Operation(c: Char) {
    val char: Char = c
    val priority: Int = c match {
      case '+' | '-' => 2
      case '*' | '/' => 1
      case _ => 0
    }

    override def toString: String = s"$char:$priority "
  }

  def calculate(x: BigDecimal, y: BigDecimal, c: Operation): BigDecimal = {
    c.char match {
      case '+' => x + y
      case '-' => x - y
      case '*' => x * y
      case '/' => x / y
    }
  }

  val line = scala.io.StdIn.readLine()

  var nums: mutable.Stack[BigDecimal] = mutable.Stack[BigDecimal]()
  var op = mutable.Stack[Operation]()

  var x = 0
  var oneOperation = false
  for (char <- line) {
    char match {
      case a if char.isDigit =>
        x = x * 10 + a.asDigit
        oneOperation = false
      case _ =>
        nums.push(x)
        x = 0
        if (oneOperation) op.push(new Operation('_'))
        else op.push(new Operation(char))
        oneOperation = true
    }
  }
  if (x > 0) nums.push(x)
  println(nums.mkString("Stack(", ", ", ")"))
  println(op.mkString("Stack(", ", ", ")"))
  breakable {
    while (op.nonEmpty) {
      val c = op.top
      op.pop
      if (c.priority == 0 | nums.size < 2) {
        print(Left("something went wrong"))
        break
      }
      val firstVal = nums.top;nums.pop
      val secondVal = nums.top;nums.pop
      nums.push(calculate(x = firstVal, y = secondVal, c))
    }
  }
  println(Right(s"Answer: ${nums.top}"))


  // DID:
  // 1) Convert my input to arrays
  // 2) Overall logic
  // 3) Priority
  // 4) Checking for validness of input

  // TODO:
  // 1) Either
  // 2) Priority: Done
  // 3) Calculation
}

