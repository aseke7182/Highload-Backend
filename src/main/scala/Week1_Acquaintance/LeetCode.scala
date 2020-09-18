package Week1_Acquaintance

object LeetCode extends App {
  def runningSum(nums: Array[Int]): Array[Int] = {
    for (i <- 1 until nums.length) {
      nums(i) += nums(i - 1);
    }
    nums;
  }
}
