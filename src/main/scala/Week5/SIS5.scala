package Week5

import java.io._

import Logic._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io.Source

object SIS5 extends App {

  def example(): Unit = {
    sealed trait Foo

    case class Bar(xs: Vector[String]) extends Foo

    case class Qux(i: Int, d: Option[Double]) extends Foo

    val foo: Foo = Qux(13, Some(14.0))

    val json = foo.asJson.noSpaces
    println(json)
  }


  case class Check(products: Array[Product])


  var products = Array[String]()
  var notProducts = Array[String]()
  var productListBoolean = false
  try {
    val file = Source.fromFile("raw.txt")
    for (line <- file.getLines) {
      if (line == "ПРОДАЖА") productListBoolean = true
      else if (line == "Банковская карта:") productListBoolean = false
      if (productListBoolean) products :+= line
      else notProducts :+= line
    }
    val productList = ProductParser(products)
    val check = Check(productList)
    val json = check.asJson.noSpaces
    val writer = new PrintWriter(new File("data.json"))
    writer.write(json)
    writer.close()


  } catch {
    case ex: FileNotFoundException => {
      println("Missing file exception")
    }

    case ex: IOException => {
      println("IO Exception")
    }
  } finally {
    println("Exiting finally...")
  }

}
