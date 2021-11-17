class Bank(val allowedAttempts: Integer = 3) {

  private val transactionsQueue: TransactionQueue = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()

  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    transactionsQueue push new Transaction(
      transactionsQueue, processedTransactions, from, to, amount, allowedAttempts
    )

    Main.thread(processTransactions)
  }

  def addAccount(initialBalance: Double): Account = {
    new Account(this, initialBalance)
  }

  def getProcessedTransactionsAsList: List[Transaction] = {
    processedTransactions.iterator.toList
  }

  private def processTransactions: Unit = {
    val transaction: Transaction = transactionsQueue.pop
    transaction.run()

    if (transaction.status == TransactionStatus.PENDING) {
      transactionsQueue push transaction
      processTransactions
    }
    else {
      processedTransactions push transaction
    }
  }
}
