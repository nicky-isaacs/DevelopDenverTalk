Immutability: A Tool for Suckers
================================
####*(And other such blanket statements)*

###Example 1:
------------------------------------------
This is an example of concurrency gone very wrong. Notice that multiple threads are mutating shared memory without any form of synchronization. This leads to non-deterministic and non-sensical behavior. Purely a motivating example as to the benefits of immutability


###Example 2:
-----------------------------------------
This is an example of using an immutable data structure to accumulate a series of results. Notice the following method definition
```scala
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
```
the call to `loop(acc.size +: acc)` is a right associative operator which prepends to the accumulator list `acc`. Something to note about this implementation:
 - Does it matter that we are prepending vs. appending to `acc`?
 - Is this accumulator ever visible outside of the thread?
 - Is is subject to concurrent modification?



 ###Example 3:
 -----------------------------------------
 This is a subtly modified version of Example 2. Notice the new definition of the inner `loop` method:
 ```scala
 @tailrec
 def loop(acc: ListBuffer[Int] = ListBuffer.empty[Int]): ListBuffer[Int] = {
   if (size == acc.size) acc
   else {
     acc.append(acc.size)
     loop(acc)
   }
 }
 ```
Instead of prepending to a list and accumulating with an immutable data-structure, we are appending to a mutable structure and passing that up the call stack. Things to think about:
 - What is the difference between a `ListBuffer` and the default `Seq`?
 - Are we subject to concurrent modification?
