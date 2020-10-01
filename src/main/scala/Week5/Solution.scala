package Week5

import java.util

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

  def subarraySum(nums: Array[Int], k: Int): Int = {
    val temp = Array.ofDim[Int](nums.length, nums.length) // How to create 2D Array

    1
  }

  def minDiffInBST(root: TreeNode): Int = {
    if (root.left == null && root.right == null) return root.value
    math.min(math.abs(root.value - minDiffInBST(root.left)), math.abs(root.value - minDiffInBST(root.right)))

    //    val st = mutable.Stack[TreeNode]()
    //    st.push(root)
    //    while (st.nonEmpty) {
    //      val now: TreeNode = st.top
    //      st.pop
    //      if (now.left != null) st.push(now.left)
    //      if (now.right != null) st.push(now.right)
    //    }
    //    1
  }
}
