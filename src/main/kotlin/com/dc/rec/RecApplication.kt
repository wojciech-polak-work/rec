package com.dc.rec

import com.dc.rec.csv.CsvReader
import com.dc.rec.csv.feeWageConverter
import com.dc.rec.csv.transactionConverter
import com.dc.rec.domain.FeeWage
import com.dc.rec.domain.Transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.ClassPathResource
import java.io.FileReader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.parse
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

@SpringBootApplication
class RecApplication

fun main(args: Array<String>) {
    runApplication<RecApplication>(*args)
}