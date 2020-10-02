package Week5

import java.util

import scala.annotation.tailrec
import scala.collection.mutable

object Solution extends App {
  println("Hello, World")

  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }

  def rangeSumBST(root: TreeNode, L: Int, R: Int): Int = {
    if (root == null) {
      return 0
    }
    var sum = 0;
    if (root.value >= L && root.value <= R) sum = root.value
    rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R) + sum
  }

  def tribonacci(n: Int): Int = {
    var now = 3
    var arr = Array[Int](0, 1, 1)
    while (n > now - 1) {
      arr :+= (arr(now - 1) + arr(now - 2) + arr(now - 3))
      now += 1
    }
    arr(n)
  }


  def minDiffInBST(root: TreeNode): Int = {
    // O(n) using traversal
    var ans = Int.MaxValue
    var prev: TreeNode = null
    helper(root)

    def helper(root: TreeNode): Unit = {
      if (root.left != null) helper(root.left)
      if (prev != null) ans = math.min(ans, math.abs(prev.value - root.value))
      prev = root
      if (root.right != null) helper(root.right)
    }

    return ans

    // Storing Variables into Array, Sort it and finding minimum difference between two adjacent
    val st = mutable.Stack[TreeNode]()
    st.push(root)
    var arr = Array[Int]()
    while (st.nonEmpty) {
      val now: TreeNode = st.top
      st.pop
      arr :+= now.value
      if (now.left != null) st.push(now.left)
      if (now.right != null) st.push(now.right)
    }
    var mini = Int.MaxValue
    val sortedArr = arr.sorted
    for (i <- 1 until sortedArr.length) {
      mini = math.min(mini, sortedArr(i) - sortedArr(i - 1))
    }
    mini
  }

  def subarraySum(nums: Array[Int], k: Int): Int = {
    val temp = Array.ofDim[Int](nums.length, nums.length) // How to create 2D Array
    //    O(n) using map
    var sum = 0
    var answer = 0
    var m = mutable.Map[Int, Int]()
    m += (0 -> 1)
    for (num <- nums) {
      sum += num
      answer += (if (m.contains(sum - k)) m(sum - k) else 0)
      if (m.contains(sum)) m(sum) += 1
      else m += (sum -> 1)
    }
    return answer

    // O(n^2) bruteforce
    var ans = 0
    for (i <- nums.indices) {
      var total = 0
      for (j <- i until nums.length) {
        total += nums(j)
        if (total == k) ans += 1
      }
    }
    ans
  }

  def longestUnivaluePath(root: TreeNode): Int = {
    var ans = 0
    helper(root)

    def helper(root: TreeNode): Int = {
      if (root == null) return 0
      var left = helper(root.left)
      var right = helper(root.right)
      if (root.left != null && root.left.value == root.value) left += 1
      else left = 0
      if (root.right != null && root.right.value == root.value) right += 1
      else right = 0
      ans = math.max(ans, left + right)
      math.max(left, right)
    }

    ans
  }

}
