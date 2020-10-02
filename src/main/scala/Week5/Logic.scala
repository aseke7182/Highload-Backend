package Week5

import Regex._

object Logic {

  case class Product(id: Int, name: String, count: Int, price: BigDecimal, total: BigDecimal)


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
        val args = line.split(" ")
        count = args(0).replaceAll(",000", "").toInt
        price = BigDecimal(args(2).replaceAll(",", "."))
      }
      if (productName.matches(line) && stoimost.matches(line)) {
        name = line.replaceAll(" ", " ")
      }
    }
    productList :+= Product(id, name, count, price, count * price)
    productList.drop(1)
  }

}
