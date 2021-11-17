class Account(val bank: Bank, initialBalance: Double) {

  val balance = new Balance(initialBalance)

  def withdraw(amount: Double): Either[Unit, String] = {
    this.synchronized {
      if (amount < 0 || amount > balance.amount) return Right("Insufficient funds")
      Left(balance.amount -= amount)
    }
  }

  def deposit(amount: Double): Either[Unit, String] = {
    this.synchronized {
      if (amount < 0) return Right("Amount cant be negative!")
      Left(balance.amount += amount)
    }
  }

  def getBalanceAmount: Double = this.synchronized {
    balance.amount
  }

  def transferTo(account: Account, amount: Double) = {
    bank addTransactionToQueue(this, account, amount)
  }

  class Balance(var amount: Double) {}
}
