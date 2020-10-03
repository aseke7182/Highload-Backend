package Week5

import Logic._

object SIS5 extends App {
  fileReader("raw.txt") match {
    case Left((products, notProducts)) => {
      parse(products, notProducts)
    }
    case Right(error) => {
      println(error)
    }
  }
}

