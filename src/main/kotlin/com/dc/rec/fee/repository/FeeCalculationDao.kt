package com.dc.rec.fee.repository

import com.dc.rec.fee.domain.FeeCalculation
import com.dc.rec.commons.logger
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class FeeCalculationDao(val mongoTemplate: MongoTemplate) {

    companion object {
        const val FEE_COLLECTION = "FEE"
    }

    private val log = logger {}

    fun save(feeCalculation: FeeCalculation) {
        log.info("Saving fee calculation for customer id: {} at {}", feeCalculation.customerId, feeCalculation.date)

        mongoTemplate.insert(feeCalculation, FEE_COLLECTION)
    }
}