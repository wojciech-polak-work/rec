package com.dc.rec.commons.csv

import com.dc.rec.fee.domain.FeeWage
import com.dc.rec.fee.domain.Transaction
import java.time.LocalDateTime.parse
import java.time.format.DateTimeFormatter.ofPattern

val dateTimeFormatter = ofPattern("dd.MM.yyyy HH:mm:ss")

val feeWageConverter = { data: Array<String> -> FeeWage(
        transactionValueLessThan = data[0].toInt(),
        feePercentageOfTransactionValue = data[1].replace(",", ".").toDouble())
}

val transactionConverter = { data: Array<String> -> Transaction(
            transactionId = data[0].toInt(),
            transactionAmount = data[1].replace(",", ".").toDouble(),
            customerFirstName = data[2],
            customerId = data[3],
            customerLastName = data[4],
            transactionDate = parse(data[5], dateTimeFormatter)
    )
}
