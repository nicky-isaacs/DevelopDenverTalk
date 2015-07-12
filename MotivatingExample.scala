import scala.annotation._
import scala.util.Random
import scala.math._

val DesiredTestSize = 50000

case class ServiceCheckResult(name: String, responseTimeInMilliseconds: Long)

// A method that takes a number and builds a list of that size
def performChecks(size: Int): Seq[ServiceCheckResult] = {

  // Inner loop function. For those familiar with Scala, this will be
  // familiar. For other, this finction works just like any other
  // function defined in out class/object, but is only visible inside
  // "buildABigListRecursivly"
  @tailrec
  def loop(acc: Seq[ServiceCheckResult] = Seq.empty[ServiceCheckResult]):
    Seq[ServiceCheckResult] = {
      if (size == acc.size) acc
      else {
        val result = performSingleServiceCheck
        loop(result +: acc)
      }
  }
  loop()
}


def performSingleServiceCheck(): ServiceCheckResult = {
  val host = Random.nextString(1) + "@victorops.com"
  val time = Math.abs(Random.nextLong()) / 1000L
  ServiceCheckResult(host, time)
}


// Helper function to time how long something takes
def timeBlock[A](body: => A): A = {
  val startTime = System.currentTimeMillis
  val result = body
  val finishTime = System.currentTimeMillis
  val totalMilliseconds = finishTime - startTime
  println(s"It took ${totalMilliseconds/1000.0} seconds to collect the results")
  result
}


println(s"Checking the response time of $DesiredTestSize calls to the service")
timeBlock {
  performChecks(DesiredTestSize)
}
