package Week5

import scala.util.matching.Regex

object Regex {
  val branch = "Филиал".r
  val BIN = "БИН".r
  val countAndPrice = "^[1-9][0-9]*\\,[0-9]{3} x [1-9][0-9]*\\,[0-9]{2}$".r
  val numberOfId : Regex = "^[1-9][0-9]*\\.$".r
  val stoimost = "^((?!Стоимость).)*$".r
  val productName = "^(?![0-9]).*".r

}
