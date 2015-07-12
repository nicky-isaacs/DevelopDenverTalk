import scala.annotation._

val DesiredListSize = 100000

// A method that takes a number and builds a list of that size
def buildABigListRecursivly(size: Int): Seq[Int] = {

  // Inner loop function. For those familiar with Scala, this will be
  // familiar. For other, this finction works just like any other
  // function defined in out class/object, but is only visible inside
  // "buildABigListRecursivly"
  @tailrec
  def loop(acc: Seq[Int] = Seq.empty[Int]): Seq[Int] = {
    if (size == acc.size) acc
    else {
      loop(acc.size +: acc)
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

println(s"Immutably building a list of size $DesiredListSize")
timeBlock {
  buildABigListRecursivly(DesiredListSize)
}
