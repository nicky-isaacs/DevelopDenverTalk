import java.util.concurrent.Executors
import scala.concurrent._
import scala.annotation._

// Lets use 10 threads
val ThreadCount = 10


// Build a new execution context using a threadpool of
// size 10
implicit val executionContext = ExecutionContext.fromExecutor(
  Executors.newFixedThreadPool(ThreadCount)
)


var myFirstInt = 0


def incrimentAndPrint() = {
  myFirstInt = myFirstInt + 1
  println(myFirstInt)
  Thread.sleep(1000)
}


// Call incrimentAndPrint while its value is less
// than n
@tailrec
def printWhileLessThan(n: Int): Unit = {
  if (myFirstInt < n) {
    incrimentAndPrint
    printWhileLessThan(n)
  } else ()
}


// Descriptinve name no?
def buildBadlyBehavingFuture() = {
  Future {
    printWhileLessThan(15)
  } onComplete {
    case _ =>
      println("Thread complete")
      System.exit(0) // Exit when I complete
  }
}


// Create ThreadCount number of Futures, which will
// be executed in the context of our ExecutionContext
(0 until ThreadCount) foreach(_ => buildBadlyBehavingFuture )
