package Week5

import java.io.{File, FileNotFoundException, IOException, PrintWriter}

import Regex._
import Classes._
import io.circe.syntax.EncoderOps

import io.circe.generic.auto._
import scala.io.Source

object Logic {

  def fromStringToBigDecimal(s: String): BigDecimal = {
    BigDecimal(s.replaceAll(",", ".").replaceAll(" ", ""))
  }

  def ProductParser(arr: Array[String]): Array[Product] = {
    var productList: Array[Product] = Array[Product]()
    var id = 0
    var name = ""
    var count = 0
    var price: BigDecimal = 0
    for (line <- arr) {
      if (numberOfId.matches(line)) {
        val product = Product(id, name, count, price, count * price)
        productList :+= product
        id = line.dropRight(1).toInt
      }
      if (countAndPrice.matches(line)) {
        val args = line.split("x")
        count = args(0).replaceAll(",000", "").replaceAll(" ", "").toInt
        price = fromStringToBigDecimal(args(1))
      }
      if (productName.matches(line) && stoimost.matches(line)) {
        name = line
      }
    }
    productList :+= Product(id, name, count, price, count * price)
    productList.drop(1)
  }

  def fileReader(filename: String): Either[(Array[String], Array[String]), String] = {
    var products = Array[String]()
    var notProducts = Array[String]()
    try {
      var productListBoolean = false
      val file = Source.fromFile(filename)
      for (line <- file.getLines) {
        val line1 = line.replaceAll(" ", " ")
        if (line1 == "ПРОДАЖА") productListBoolean = true
        else if (line1 == "Банковская карта:") productListBoolean = false
        if (productListBoolean) products :+= line1
        else notProducts :+= line1
      }
      Left((products, notProducts))
    } catch {
      case ex: FileNotFoundException => {
        Right("Missing file exception")
      }
      case ex: IOException => {
        Right("IO Exception")
      }
      case _ => Right("Something went wrong")
    }
  }

  def notProductParser(notProducts: Array[String], productList: Array[Product]): Check = {
    var branch = ""
    var BIN = ""
    var checkNumber = ""
    var total: BigDecimal = 0
    var time = ""
    var location = ""
    for (line <- notProducts) {
      if (branchRegex.matches(line)) {
        branch = line.substring(7)
      }
      if (BINRegex.matches(line)) {
        BIN = line.substring(4)
      }
      if (checkNumberRegex.matches(line)) {
        checkNumber = line.substring(5)
      }
      if (totalRegex.matches(line)) {
        total = fromStringToBigDecimal(line)
      }
      if (locationRegex.matches(line)) {
        location = line
      }
      if(timeRegex.matches(line)){
        time = line.substring(7)
      }
    }
    Check(branch, BIN, checkNumber, productList, total, time, location)
  }

  def parse(products: Array[String], notProducts: Array[String]): Unit = {
    val productList = ProductParser(products)
    val check = notProductParser(notProducts, productList)
    val json = check.asJson.toString
    val writer = new PrintWriter(new File("data.json"))
    writer.write(json)
    writer.close()
  }

}
