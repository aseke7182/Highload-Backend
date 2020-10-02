package Week5

import java.io.{FileNotFoundException, IOException}

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

  case class Product(name: String, count: Int, price: BigDecimal, total: BigDecimal)

  val branch = "Филиал".r
  val BIN = "БИН".r
  val countAndPrice = "^[0-9]+\\,[0-9]{3} [x] [0-9]+\\,[0-9]{2}$".r
  var text = ""
  try {
    val file = Source.fromFile("raw.txt")
    for (line <- file.getLines) {

    }
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
