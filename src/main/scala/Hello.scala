import scala.annotation.tailrec

object Hello extends App {
  //Syntax
  def square(x: Int) = x * x;
  println("Hello World")
  println(16.toHexString);
  val a = 3;
  //  a = a + 2; immutable


  // Methods
  val x = 0
  def f(y: Int) = y + 1
  val result = {
    val x = f(3)
    x * x
  } + x
  println(result)


  //tailrec
  def sum(a: Int, b: Int): Int = {
    def loop(x: Int, acc: Int): Int =
      if (x > b) acc
      else loop(x + 1, acc + x)

    loop(a, 0)
  }
  println(sum(1, 10))


  // Creating List
  val heterogeneousList: List[Any] = List(1, "1", '1', "Hello")
  println(heterogeneousList)
  val nums = 1 :: 2 :: 3 :: 4 :: Nil
  println(nums)
  val nums1 = Nil.::(4).::(3).::(2).::(1)
  println(nums1)

  // Sorting List
  val cond: (Int, Int) => Boolean = (x, y) => x < y
  def insert(x: Int, xs: List[Int]): List[Int] =
    xs match {
      case List() => x :: Nil
      case y :: ys =>
        if (cond(x, y)) x :: y :: ys
        else y :: insert(x, ys)
    }
  println(insert(2, 1 :: 3 :: Nil))
  println(insert(1, 2 :: 3 :: Nil))
  println(insert(3, 1 :: 2 :: Nil))

  //Map
  println(Some(1).map(x => x+ 1 ))
  val arr = Array(2,4).filter(x => x%2 ==0 )
  println(arr.mkString("Array(", ", ", ")"))
  // println(arr) not working properly
}