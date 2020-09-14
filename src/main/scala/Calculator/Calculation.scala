package Calculator
import scala.collection.mutable

class Calculation {

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
      case '-' => y - x
      case '*' => x * y
      case '/' => y / x
    }
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
          if (oneOperation) op.push(new Operation('_'))
          else op.push(new Operation(char))
          oneOperation = true
      }
    }

    if (x > 0) nums.push(x)
    while (op.nonEmpty) {
      val c = op.top
      op.pop
      if (c.priority == 0 | nums.size < 2) {
        return Left("Something went wrong")
      }
      val firstVal = nums.top;
      nums.pop
      val secondVal = nums.top;
      nums.pop
      nums.push(calculate(x = firstVal, y = secondVal, c))
    }
    Right((nums.top).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
  }
}
