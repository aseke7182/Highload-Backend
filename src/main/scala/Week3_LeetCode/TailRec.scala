package Week3_LeetCode

import scala.annotation.tailrec

object TailRec extends App {
  @tailrec
  def isValInArray(arr: Array[Int], value: Int, index: Int = 0 ): Boolean = {
    if( index == arr.length) return false
    if(arr(index)==value) return true
    isValInArray(arr,value,index+1)
  }

}
