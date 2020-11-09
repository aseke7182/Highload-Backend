package Scala_tutorial


object Main extends App {
  def OOP(): Unit = {
    val x = null
    val y: String = null
    // val z : Int = null  # error type mismatch
    println(x)
    println(y)
  }

  def Imperative_Language(): Unit = {
    class BankAccount {
      private var balance = 0

      def deposit(amount: Int): Int = {
        if (amount > 0) balance = balance + amount
        balance
      }

      def withdraw(amount: Int): Int =
        if (0 < amount && amount <= balance) {
          balance = balance - amount
          balance
        } else throw new Error("insufficient funds")
    }
    val account = new BankAccount;
    println(account deposit 30)
    val secondAccount = new BankAccount;
    val copyAccount = account
    println(copyAccount deposit 10)
    println(account deposit 0)
    println(secondAccount deposit 5)
    println(account == secondAccount)
    println(account == copyAccount)
  }

  def caseClass_vs_Class(): Unit = {

    class Note(_name: String, _duration: String, _octave: Int) extends Serializable {

      // Constructor parameters are promoted to members
      val name: String = _name
      val duration: String = _duration
      val octave: Int = _octave

      // Equality redefinition
      override def equals(other: Any): Boolean = other match {
        case that: Note =>
          (that canEqual this) &&
            name == that.name &&
            duration == that.duration &&
            octave == that.octave
        case _ => false
      }

      def canEqual(other: Any): Boolean = other.isInstanceOf[Note]

      // Java hashCode redefinition according to equality
      override def hashCode(): Int = {
        val state = Seq(name, duration, octave)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
      }

      // toString redefinition to return the value of an instance instead of its memory addres
      override def toString = s"Note($name,$duration,$octave)"

      // Create a copy of a case class, with potentially modified field values
      def copy(name: String = name, duration: String = duration, octave: Int = octave): Note =
        new Note(name, duration, octave)

    }

    object Note {

      // Constructor that allows the omission of the `new` keyword
      def apply(name: String, duration: String, octave: Int): Note =
        new Note(name, duration, octave)

      // Extractor for pattern matching
      def unapply(note: Note): Option[(String, String, Int)] =
        if (note eq null) None
        else Some((note.name, note.duration, note.octave))

    }

    val c3 = Note("C", "Quarter", 3)
    println(c3.toString)
    val d = Note("D", "Quarter", 3)
    println(c3.equals(d))
    val c4 = c3.copy(octave = 4)
    println(c4.toString)
  }

  def lazy_Evaluation(): Unit = {
    val builder = new StringBuilder

    val x = {
      builder += 'x';
      1
    }
    lazy val y = {
      builder += 'y';
      2
    }

    def z = {
      builder += 'z';
      3
    }

    z + y + x + z + y + x
    println(builder.result())
  }
  lazy_Evaluation()
}
