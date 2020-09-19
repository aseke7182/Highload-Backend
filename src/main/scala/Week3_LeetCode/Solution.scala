package Week3_LeetCode

import scala.annotation.tailrec

object Solution extends App {

  def kidsWithCandies(candies: Array[Int], extraCandies: Int): Unit = {
    var maxi = -1;
    candies.foreach(x => maxi = math.max(maxi, x))
    var ans = new Array[Boolean](candies.length)
    for (i <- candies.indices) {
      if (candies(i) + extraCandies >= maxi) ans(i) = true
      else ans(i) = false
    }
    ans
  }

  def smallerNumbersThanCurrent(nums: Array[Int]): Unit = {
    var ans = new Array[Int](nums.length)
    for (i <- nums.indices) {
      var cnt = 0
      for (number <- nums) {
        if (number < nums(i)) cnt = cnt + 1
      }
      ans(i) = cnt
    }
    ans
  }

  def repeatedNTimes(A: Array[Int]): Unit = {
    var arr = new Array[Int](10001)
    var maxi = -1;
    var ans = -1;
    for (x <- A) {
      arr(x) += 1
      var temp = arr(x)
      if (temp > maxi) {
        maxi = temp
        ans = x
      }
    }
    ans
  }

  def decompressRLElist(nums: Array[Int]): Unit = {
    var ans = Array[Int]()
    for (i <- 0 until (nums.length / 2)) {
      for (times <- 0 until nums(2 * i)) {
        ans :+= (nums(2 * i + 1))
      }
    }
    ans
  }

  def sumZero(n: Int): Unit = {
    var ans = Array[Int]()
    if (n % 2 != 0) ans :+= 0
    for (i <- 1 to (n / 2)) {
      ans :+= i
      ans :+= (i * (-1))
    }
    println(ans.mkString(" "))
  }

  def kWeakestRows(mat: Array[Array[Int]], k: Int): Unit = {
    var strength = new Array[(Int, Int)](mat.length)
    for (row <- mat.indices) {
      var cnt = 0
      for (element <- mat(row)) {
        cnt += element
      }
      strength(row) = (cnt, row)
    }
    println(strength.sorted.slice(0, k).map(_._2).mkString(" "))
  }

  def buildArray(target: Array[Int], n: Int): Unit = {
    var ans = List[String]()
    var index = 0
    for (i <- 1 to target(target.length - 1)) {
      ans :+= "Push"
      if (target(index) != i) {
        ans :+= "Pop"
      } else {
        index += 1
      }
    }
    ans
  }


  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }

  def fromBinaryStringToDecimal(str: String): Unit = {
    var ans = 0
    var exp = 0
    for (i <- (str.length - 1) to 0 by -1) {
      ans += (math.pow(2, exp).toInt) * (str(i) - 48)
      exp += 1
    }
    println(ans)
  }

  def getDecimalValue(head: ListNode): Any = {

    @tailrec
    def getString(head: ListNode, res: String = ""): String = {
      if (head == null) return res
      getString(head.next, res + head.x)
    }

    Integer.parseInt(getString(head), 2)
  }

}
