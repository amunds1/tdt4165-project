import scala.collection.mutable

class TransactionQueue {
  private val queue = mutable.Queue[Transaction]()

  // Remove and return the first element from the queue
  def pop: Transaction = this.synchronized {
    queue.dequeue()
  }

  // Return whether the queue is empty
  def isEmpty: Boolean = this.synchronized {
    queue.isEmpty
  }

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = this.synchronized {
    queue += t
  }

  // Return the first element from the queue without removing it
  def peek: Transaction = this.synchronized {
    queue.front
  }

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = this.synchronized {
    queue.iterator
  }
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttempts: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt = 0

  override def run: Unit = {

    def doTransaction() = {
      val withdraw = this.from.withdraw(amount)

      withdraw match {
        case Left(_) => {
          to.deposit(amount)
          status = TransactionStatus.SUCCESS
        }

        case Right(_)
        => {
          attempt += 1
          if (attempt >= allowedAttempts) {
            status = TransactionStatus.FAILED
          }
        }
      }
    }

    if (status == TransactionStatus.PENDING) {
      this.synchronized {
        doTransaction
        Thread.sleep(50)
      }
    }
  }
}

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}
