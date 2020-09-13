package Calculator

object Main extends App {

//  val pr = Array[Int]('*','/','+','-')
//  pr('*') = 1
//  pr('/') = 1
//  pr('+') = 2
//  pr('-') = 2

  class Operation(c:Char){
    val char: Char = c
//    val priority: Int = pr(c)
  }

  def multi(x: BigDecimal, y: BigDecimal): BigDecimal = x * y

  def div(x: BigDecimal, y: BigDecimal): BigDecimal = x / y

  def plus(x: BigDecimal, y: BigDecimal): BigDecimal = x + y

  def minus(x: BigDecimal, y: BigDecimal): BigDecimal = x - y

  val line = scala.io.StdIn.readLine()

  var nums: Array[BigDecimal] = Array[BigDecimal]()
  var op = Array[Char]()

  var x = 0
  for( char <- line ){
    char match {
      case a if char.isDigit =>
        x = x * 10 + a.asDigit
      case _ =>
        nums:+= x
        x = 0
        op:+= char
    }
  }
  if(x>0) nums:+= x
  println(nums.mkString("Array(", ", ", ")"))
  println(op.mkString("Array(", ", ", ")"))

  // DID:
  // 1) Convert to arrays my input
  // 2) Overall logic

  // TODO:
  // 1) Either
  // 2) Priority
  // 3) Calculation
}

