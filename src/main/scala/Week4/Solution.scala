package Week4

import java.time.Year

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV

import scala.collection.immutable.HashSet
import scala.collection.mutable

object Solution extends App {
  def maxProduct(nums: Array[Int]): Any = {
    var max = -1
    var secondmax = -1
    for (num <- nums) {
      if (num > max) {
        secondmax = max
        max = num
      }
      else if (num > secondmax) {
        secondmax = num
      }
    }
    (max - 1) * (secondmax - 1)
    //    var arr = nums.sorted.takeRight(2)
    //    (arr(0) - 1) * (arr(1) - 1)
  }

  def average(salary: Array[Int]): Double = {
    var total = 0
    var maxi = 1
    var mini = 1e8
    for (money <- salary) {
      maxi = math.max(maxi, money)
      mini = math.min(mini, money)
      total += money
    }
    ((total - maxi - mini) / (salary.length - 2))
  }

  def findPairs(nums: Array[Int], k: Int): Any = {
    // couldn't realize using map in O(n)
    var s = Set[(Int, Int)]()
    for ((num, index) <- nums.zipWithIndex) {
      for (i <- ((index + 1) until nums.length)) {
        val other = nums(i)
        if (math.abs(num - other) == k) {
          s += ((math.max(num, other), math.min(other, num)))
        }
      }
    }
    s.size
  }

  def dayOfTheWeek(day: Int, month: Int, year: Int): Any = {


    def visGod(year: Int): Int = {
      if ((year % 4 == 0 && year % 100 != 0) | year % 400 == 0) return 1
      0
    }

    def sumUpYear(year: Int, days: List[Int]): Int = {
      var sum = 0
      for (i <- 0 until 12) {
        if (i == 1) sum += visGod(year)
        sum += days(i)
      }
      sum
    }

    def sumUpMonth(month: Int, year: Int, days: List[Int]): Int = {
      var total = 0
      total += days(month)
      if (month == 1) total += visGod(year)
      total
    }

    val week = List[String]("Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    val days = List[Int](31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    var totalDays = 4
    for (i <- 1971 until year) {
      totalDays += sumUpYear(i,days)
    }
    for (i <- 0 until (month-1)) {
      totalDays += sumUpMonth(i, year, days)
    }
    week((totalDays + day) % 7)
  }
}
