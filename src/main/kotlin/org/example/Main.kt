package org.example

import java.util.concurrent.Executors.newFixedThreadPool

fun main() {
    val executor = newFixedThreadPool(4)
    val firstAccount = Account("first", 0)
    val secondAccount = Account("second", 10)
    for (i in 0 .. 10) {
        val transfer = if (i % 2 == 0) {
            Transfer(from = firstAccount, to = secondAccount, amount = 2)
        } else {
            Transfer(from = secondAccount, to = firstAccount, amount = 2)
        }
        executor.submit(transfer)
    }
    executor.shutdown()
}