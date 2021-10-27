object taskOne {
    def numberArrayGenerator(): Array[Int] = {
        var result: Array[Int] = new Array[Int](50)

        for(i <- 1 to 50) {
            result(i - 1) = i
        }

        return result
    }

    def sumElementsInArray(array: Array[Int]): Int = {
        var sum: Int = 0

        for (element <- array) {
            sum += element
        }
        return sum
    }

    def sumElementsRecursively(array: Array[Int]): Int = {
        array match {
            case Array() => {
                0
            }

            case Array(_, _*) => {
                return array.head + sumElementsRecursively(array.tail)
            }
        }
    }

    def nthFibonacci(n: BigInt): BigInt = {
        if (n <= 1) {
            return n
        } else {
            return nthFibonacci(n - 1) + nthFibonacci(n - 2)
        }
    }

    def main(args: Array[String]) {
        for (number <- numberArrayGenerator()) {
            println(number)
        }

        println(sumElementsInArray(numberArrayGenerator()))
        println(sumElementsRecursively(numberArrayGenerator()))
        println(nthFibonacci(3))
    }
}