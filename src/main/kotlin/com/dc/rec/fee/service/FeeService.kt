package com.dc.rec.fee.service


import com.dc.rec.fee.repository.FeeCalculationDao
import com.dc.rec.fee.model.FeeCalculation
import com.dc.rec.fee.model.FeeWage
import com.dc.rec.fee.model.Transaction
import com.dc.rec.commons.FEE_WAGES_CACHE_NAME
import com.dc.rec.commons.TRANSACTIONS_CACHE_NAME
import com.dc.rec.commons.cache
import com.dc.rec.commons.logger
import com.dc.rec.fee.controller.dto.FeeDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FeeService(val feeCalculationDao: FeeCalculationDao) {

    private val log = logger {}

    fun getFees(customerIds: List<String>): List<FeeDto> {
        val calculatedFees = customerIds.mapNotNull { calculateFee(it) }

        saveFees(calculatedFees)

        return calculatedFees
    }

    fun getAllFees(): List<FeeDto> {
        val calculatedFees = (cache[TRANSACTIONS_CACHE_NAME] as List<Transaction>?)!!
                .groupBy { it.customerId }
                .map { it.key }
                .mapNotNull { calculateFee(it) }

        saveFees(calculatedFees)

        return calculatedFees
    }

    private fun saveFees(calculatedFees: List<FeeDto>) {
        calculatedFees
                .map { FeeCalculation(customerId = it.customerId, totalFee = it.totalFee) }
                .forEach {
                    feeCalculationDao.save(it)
                }
    }

    private fun calculateFee(customerId: String): FeeDto? {
        log.info("Calculating fee for customer id: {}", customerId)

        val transactions: List<Transaction> = (cache[TRANSACTIONS_CACHE_NAME] as List<Transaction>?)!!
        val transactionsByCustomerId = transactions.filter { it.customerId == customerId }
        if (transactionsByCustomerId.isNullOrEmpty()) return null

        val totalTransactionSum = transactionsByCustomerId.sumOf { it.transactionAmount }
        val feeWage = findFeeWage((cache[FEE_WAGES_CACHE_NAME] as List<FeeWage>?)!!, totalTransactionSum)
        val calculatedFee = calculateFee(totalTransactionSum, feeWage)

        val transaction = transactionsByCustomerId.first()

        return FeeDto(
                customerFirstName = transaction.customerFirstName,
                customerLastName = transaction.customerLastName,
                customerId = transaction.customerId,
                transactionCount = transactionsByCustomerId.count(),
                totalTransactionSum = "%.2f".format(totalTransactionSum),
                totalFee = "%.2f".format(calculatedFee),
                lastTransactionDate = findLatestTransactionDate(transactionsByCustomerId)
        )
    }

    private fun calculateFee(totalTransactionSum: Double, feeWage: FeeWage) =
            (totalTransactionSum * feeWage.feePercentageOfTransactionValue) / 100

    private fun findFeeWage(feeWages: List<FeeWage>, totalTransactionSum: Double): FeeWage {
        val sortedWages = feeWages.sortedBy { it.transactionValueLessThan }
        val feeWage = sortedWages.find { totalTransactionSum < it.transactionValueLessThan }

        if (feeWage != null) return feeWage

        return sortedWages.last()
    }

    private fun findLatestTransactionDate(transactionsByCustomerId: List<Transaction>): LocalDateTime {
        return transactionsByCustomerId
                .map { transaction -> transaction.transactionDate }
                .maxByOrNull { date -> date }!!
    }
}