package com.github.krystiankowalik.transactionlistprovider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TransactionlistproviderApplication

fun main(args: Array<String>) {
    runApplication<TransactionlistproviderApplication>(*args)
}
