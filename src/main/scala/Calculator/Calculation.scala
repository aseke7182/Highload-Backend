package Calculator

import scala.collection.mutable

class Calculation {

  class Operation(c: Char) {
    val char: Char = c
    val priority: Int = c match {
      case '+' | '-' => 1
      case '*' | '/' => 2
      case _ => 0
    }

    override def toString: String = s"$char:$priority "
  }

  def calculate(x: BigDecimal, y: BigDecimal, c: Operation): BigDecimal = {
    c.char match {
      case '+' => x + y
      case '-' => y - x
      case '*' => x * y
      case '/' => y / x
    }
  }

  def calculation(): Unit ={
    val c = op.top
    op.pop
    val firstVal = nums.top
    nums.pop
    val secondVal = nums.top;
    nums.pop
    nums.push(calculate(x = firstVal, y = secondVal, c))
  }

  var nums: mutable.Stack[BigDecimal] = mutable.Stack[BigDecimal]()
  var op: mutable.Stack[Operation] = mutable.Stack[Operation]()

  var x = 0
  var oneOperation = false

  def start(line: String): Either[String, BigDecimal] = {
    for (char <- line) {
      char match {
        case a if char.isDigit =>
          x = x * 10 + a.asDigit
          oneOperation = false
        case _ =>
          nums.push(x)
          x = 0
          if (oneOperation) {
            return Left("Something went wrong")
          }
          val newOp = new Operation(char)
          if (newOp.priority == 0) return Left("Something went wrong")

          while (op.nonEmpty && op.top.priority >= newOp.priority) {
            calculation()
          }
          op.push(newOp)
          oneOperation = true
      }
    }
    if (x > 0 | line(line.length - 1) == '0') nums.push(x)
    while (op.nonEmpty & nums.length > 1) {
      calculation()
    }
    if (op.nonEmpty) return Left("Something went wrong")
    Right((nums.top).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  }
}
