package com.github.krystiankowalik.transactionlistprovider.controller

import com.github.krystiankowalik.transactionlistprovider.model.Transaction
import com.github.krystiankowalik.transactionlistprovider.service.TransactionService
import org.apache.xpath.operations.Bool
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionController(val tranasctionService: TransactionService) {

    @GetMapping
    fun getRecentTransactions(@RequestParam(required = false, defaultValue = false.toString()) fresh: Boolean): ResponseEntity<List<Transaction>>
            = getRecentTransactionsFromServer(fresh)

    private fun getRecentTransactionsFromServer(fresh: Boolean) =
            ResponseEntity<List<Transaction>>(tranasctionService.getRecentTransactions(fresh)
                    .map { it.adjustFormat() }, HttpStatus.OK)

}