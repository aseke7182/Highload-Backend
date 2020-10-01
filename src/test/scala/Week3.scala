import Week3_LeetCode.TailRec._
import org.scalatest.FunSuite

class Week3 extends FunSuite {
  test("TailRec") {
    val arr = Array[Int](1, 2, 3, 4)
    assert(isValInArray(arr, 5) === false)
    assert(isValInArray(arr, 4) === true)
    assert(isValInArray(arr, 0) === false)
  }
}
