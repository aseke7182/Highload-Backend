import org.scalatest.FunSuite
import Week2_Calculator.Calculation
import Week2_Calculator.Constants._
class CalculatorTest extends FunSuite {
  test("CalculatorTest"){
    val test1 = "2+2"
    val test2 = "2*2+2*2"
    val test3 = "2"
    val test4 = "2+"
    val test5 = "22++44"
    val test6 = "41+15-3"
    val test7 = "44/2"
    val result = new Calculation start (test1)
    val result2 = new Calculation start (test2)
    val result3 = new Calculation start (test3)
    val result4 = new Calculation start (test4)
    val result5 = new Calculation start (test5)
    val result6 = new Calculation start (test6)
    val result7 = new Calculation start (test7)
    assert(result === Right(4.0))
    assert(result2 === Right(8.0))
    assert(result3 === Right(2.0))
    assert(result4 === Left(error))
    assert(result5 === Left(error))
    assert(result6 === Right(53.0))
    assert(result7 === Right(22.0))
  }

}
