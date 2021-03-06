package com.dc.rec.fee.controller.dto

import java.time.LocalDateTime

data class FeeDto(
        val customerFirstName: String,
        val customerLastName: String,
        val customerId: String,
        val transactionCount: Int,
        val totalTransactionSum: String,
        val totalFee: String,
        val lastTransactionDate: LocalDateTime
)