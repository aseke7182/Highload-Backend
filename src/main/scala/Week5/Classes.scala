package Week5

object Classes {

  case class Product(id: Int, name: String, count: Int, price: BigDecimal, total: BigDecimal)

  case class Check(branch: String,
                   BIN: String,
                   checkNumber: String,
                   products: Array[Product],
                   total: BigDecimal,
                   time: String,
                   location: String
                  )

}
