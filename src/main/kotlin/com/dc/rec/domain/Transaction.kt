package com.dc.rec.domain

import java.time.LocalDateTime

data class Transaction(
        val transactionId: Int,
        val transactionAmount: Double,
        val customerFirstName: String,
        val customerId: String,
        val customerLastName: String,
        val transactionDate: LocalDateTime
)