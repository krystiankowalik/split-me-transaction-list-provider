package com.github.krystiankowalik.transactionlistprovider.io

import com.github.krystiankowalik.transactionlistprovider.model.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, String> {

}