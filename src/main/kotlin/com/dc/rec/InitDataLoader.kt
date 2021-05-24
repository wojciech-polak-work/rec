package com.dc.rec

import com.dc.rec.csv.CsvReader
import com.dc.rec.csv.feeWageConverter
import com.dc.rec.csv.transactionConverter
import com.dc.rec.helpers.FEE_WAGES_CACHE_NAME
import com.dc.rec.helpers.TRANSACTIONS_CACHE_NAME
import com.dc.rec.helpers.cache
import com.dc.rec.helpers.logger
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileReader

@Component
class InitDataLoader(val csvReader: CsvReader = CsvReader()) : ApplicationRunner {

    private val log = logger {}

    override fun run(args: ApplicationArguments?) {
        val feeWagesFile = ClassPathResource("fee_wages.csv").file
        val transactionsFile = ClassPathResource("transactions.csv").file

        log.info("Loading fee wages file")
        initFeeWages(feeWagesFile)

        log.info("Loading fee transactions file")
        initTransactions(transactionsFile)
    }

    private fun initFeeWages(file: File) {
        FileReader(file).use {
            val feeWages = csvReader.read(it, feeWageConverter)

            cache[FEE_WAGES_CACHE_NAME] = feeWages
        }
    }

    private fun initTransactions(file: File) {
        FileReader(file).use {
            val transactions = csvReader.read(it, transactionConverter)

            cache[TRANSACTIONS_CACHE_NAME] = transactions
        }
    }
}

