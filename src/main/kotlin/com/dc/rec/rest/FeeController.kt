package com.dc.rec.rest

import com.dc.rec.rest.dto.FeeDto
import com.dc.rec.service.FeeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/fee")
class FeeController(private val feeService: FeeService) {

    @GetMapping("/{customerIds}", "")
    fun getFees(@PathVariable customerIds: String?): ResponseEntity<List<FeeDto>> {

        return if (customerIds.isNullOrBlank() || customerIds.equals("ALL", ignoreCase = true)) {
            ResponseEntity.ok(feeService.getAllFees())
        } else {
            val customerIdsAfterSplit = customerIds.trim().split(",")

            ResponseEntity.ok(feeService.getFees(customerIdsAfterSplit))
        }
    }
}