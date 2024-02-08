package org.example

import jdk.jfr.DataAmount
import jdk.jfr.TransitionFrom
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

private val lock = ReentrantLock()
private val condition = lock.newCondition()

private fun summaryBalance(firstAccount: Account, secondAccount: Account) = firstAccount.balance + secondAccount.balance

class Transfer(
    val from: Account,
    val to: Account,
    val amount: Int,
): Runnable {
    override fun run() {
        lock.withLock {
            while (from.balance < amount) {
                condition.await()
            }
            println("${Thread.currentThread().name}: transfer from=${from.name} to=${to.name} ammount=$amount")
            from.balance -= amount
            to.balance += amount
            println("${Thread.currentThread().name}: summaryBalance=${summaryBalance(from, to)}, ${from.name}Balance=${from.balance}, ${to.name}Balance=${to.balance}")
            println()
            condition.signalAll()
        }
    }
}