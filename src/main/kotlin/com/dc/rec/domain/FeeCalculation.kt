package com.dc.rec.domain

import java.time.LocalDateTime.now
import java.util.*


data class FeeCalculation(
        val id: String = UUID.randomUUID().toString(),
        val customerId: String,
        val totalFee: String,
        val date: String = now().toString()
)