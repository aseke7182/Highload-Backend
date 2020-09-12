import org.scalatest.FunSuite

class SIS1 extends FunSuite {
  test("FirstTest"){
    assert(Hello.square(2) === 4);
    assert(Hello.square(3) === 9);
    assert(Hello.square(3) !== 11);
  }
  test("LeetCodeTest"){
    var nums = Array(1,2,3,4)
    var ans = Array(1,3,6,10)
    assert(LeetCode.runningSum(nums) === ans )
  }
}
