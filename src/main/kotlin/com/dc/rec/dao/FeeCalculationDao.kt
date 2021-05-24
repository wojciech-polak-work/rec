package com.dc.rec.dao

import com.dc.rec.domain.FeeCalculation
import com.dc.rec.helpers.logger
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