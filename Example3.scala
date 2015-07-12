import scala.annotation._
import scala.collection.mutable._

val DesiredListSize = 100000
// A method that takes a number and builds a list of that size
def buildABigListRecursivly(size: Int): Seq[Int] = {

  // Inner loop function. For those familiar with Scala, this will be
  // familiar. For other, this finction works just like any other
  // function defined in out class/object, but is only visible inside
  // "buildABigListRecursivly"
  @tailrec
  def loop(acc: ListBuffer[Int] = ListBuffer.empty[Int]): ListBuffer[Int] = {
    if (size == acc.size) acc
    else {
      acc.append(acc.size)
      loop(acc)
    }
  }

  loop()
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

println(s"Mutably building a list of size $DesiredListSize")
timeBlock {
  buildABigListRecursivly(DesiredListSize)
}
