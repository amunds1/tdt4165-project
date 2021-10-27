object taskTwo {
  def createThreadWithFunction(callback: () => Unit): Thread = {
    val thread = new Thread(
        new Runnable {
            def run() {
                callback()
            }
        }
    )

    return thread

    } 

    private var counter: Int = 0

    def increaseCounter(): Unit = {
        counter.synchronized {
            counter += 1 
        }
    }

    def printCounter(): Unit = {
        println(counter)
    }

    object A {
        lazy val one = B.two
    }

    object B {
        lazy val one = A.one
        lazy val two = 30
    }

    def main(args: Array[String]) {
        createThreadWithFunction(increaseCounter).start
        createThreadWithFunction(increaseCounter).start
        createThreadWithFunction(printCounter).start
    }
}