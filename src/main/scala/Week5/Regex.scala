package Week5

import scala.util.matching.Regex

object Regex {
  val branchRegex = "^Филиал [A-Za-zА-Яа-я ]+".r
  val BINRegex = "^БИН [0-9]+$".r
  val checkNumberRegex = "^Чек №[1-9]+[0-9]*$".r
  val totalRegex = "^((([1-9][0-9]* )+[0-9]+)|(1-9][0-9]+))\\,[0-9]{2}$".r
  val timeRegex = "^Время: ([0-9]{2}\\.[0-9]{2}\\.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2})".r
  val locationRegex = "(г\\. ?[а-яА-Я-]* ?, ?[а-яА-Я-]* ?, ?[а-яА-Я- ]* ?, ?[0-9]* ?, ?[0-9а-яА-Я-]*)".r
  val countAndPrice = "^([1-9][0-9]* )*[1-9][0-9]*\\,[0-9]{3} x ([1-9][0-9]* )*[1-9][0-9]*\\,[0-9]{2}$".r
  val numberOfId: Regex = "^[1-9][0-9]*\\.$".r
  val stoimost = "^((?!Стоимость).)*$".r
  val productName = "^(?![0-9]).*".r

}
