package com.github.krystiankowalik.transactionlistprovider.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "transactions")
data class Transaction(@Id val id: String = "",
                       val amount: String = "",
                       val currency: String = "",
                       val description: String = "",
                       val date: String = ""
) {
    fun adjustFormat() = Transaction(
            id = this.id,
            amount = this.amount.replace(",", "."),
            currency = this.currency,
            description = this.description,
            date = this.date
    )
}