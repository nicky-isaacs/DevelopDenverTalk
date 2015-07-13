import scala.annotation._
import scala.collection.mutable.ListBuffer

val NumberOfShoesDesired = 50000

case class Shoe(brand: String, color: String, size: Float)

val Brands = "Nike" :: "Adidas" :: "Converse" :: "Reebok" :: Nil

val Colors = "red" :: "blue" :: "green" :: "black" :: Nil


// A method that returns the most recent n number of shoes
// from our inventory
def getShoes(size: Int): Seq[Shoe] = {
  // Inner loop function. For those familiar with Scala, this will be
  // familiar. For other, this finction works just like any other
  // function defined in out class/object, but is only visible inside
  // "getShoes"
  @tailrec
  def loop(acc: ListBuffer[Shoe] = ListBuffer.empty[Shoe]): ListBuffer[Shoe] = {
    if (size == acc.size) acc
    else {
      acc.prepend(getNextShoe)
      loop(acc)
    }
  }
  loop()
}


// Returns the next shoe in the inventory
def getNextShoe(): Shoe = {
  Shoe(
    Brands.randomElement,
    Colors.randomElement,
    scala.util.Random.nextInt(14)
  )
}


// Helper function to time how long something takes
def timeBlock[A](body: => A): A = {
  val startTime = System.currentTimeMillis
  val result = body
  val finishTime = System.currentTimeMillis
  val totalMilliseconds = finishTime - startTime
  println(s"Block took ${totalMilliseconds/1000.0} seconds to complete")
  result
}

println(s"Immutably building a list of $NumberOfShoesDesired shoes")
timeBlock {
  val shoes = getShoes(NumberOfShoesDesired)
  println(shoes.head)
}



implicit class SeqUtils[A](s: Seq[A]) {

  def randomElement(): A = {
    val index = scala.util.Random.nextInt(s.size)
    s(index)
  }

}
