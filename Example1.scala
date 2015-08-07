import java.util.concurrent.Executors
import scala.concurrent._
import scala.annotation._

// Lets use 10 threads
val ThreadCount = 10

val Lock = new Object

// Build a new execution context using a threadpool of
// size 10
implicit val executionContext = ExecutionContext.fromExecutor(
  Executors.newFixedThreadPool(ThreadCount)
)


var myFirstInt: Int = 0


def incrementAndPrint() = {
  myFirstInt = myFirstInt + 1
  println(myFirstInt)
  Thread.sleep(100)
}


// Call incrementAndPrint while its value is less
// than n
@tailrec
def printWhileLessThan(n: Int): Unit = {
  if (myFirstInt < n) {
    incrementAndPrint
    printWhileLessThan(n)
  } else ()
}


// Descriptive name, no?
def buildBadlyBehavingFuture(): Future[Unit] = {
  val f = Future(printWhileLessThan(15))
  f onComplete {
    case _ => println("Thread complete")
  }
  f
}


// Create ThreadCount number of Futures, which will
// be executed in the context of our ExecutionContext
val futures = (0 until ThreadCount).map(_ => buildBadlyBehavingFuture ).toSeq
Future.sequence(futures).map(_ => System.exit(0))
