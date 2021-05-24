package com.dc.rec.rest


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus.OK


@SpringBootTest
class FeeControllerTest {

    @Autowired
    lateinit var feeController: FeeController

    @Test
    fun shouldFetchAllFeesTest() {
        //given
        val customerIds = "aLl"

        //when
        val response = feeController.getFees(customerIds)

        //then
        assertEquals(OK, response.statusCode)
        assertEquals(5, response.body!!.size)
    }

    @Test
    fun shouldFetchNoneFeesTest() {
        //given
        val customerIds = "qwerty"

        //when
        val response = feeController.getFees(customerIds)

        //then
        assertEquals(OK, response.statusCode)
        assertEquals(0, response.body!!.size)
    }

    @Test
    fun shouldFetchTwoFeesTest() {
        //given
        val customerIds = "1,3"

        //when
        val response = feeController.getFees(customerIds)

        //then
        assertEquals(OK, response.statusCode)
        assertEquals(2, response.body!!.size)
        assertEquals("1", response.body!![0].customerId)
        assertEquals("3", response.body!![1].customerId)
    }

    @Test
    fun shouldCorrectlySumTotalTransactions() {
        //given
        val customerIds = "4"

        //when
        val response = feeController.getFees(customerIds)

        //then
        assertEquals(OK, response.statusCode)
        assertEquals("4", response.body!![0].customerId)
        assertEquals(6, response.body!![0].transactionCount)
        assertEquals("2059,02", response.body!![0].totalTransactionSum)
    }

    @Test
    fun shouldCorrectlyCalculateFee() {
        //given
        val customerIds = "4"

        //when
        val response = feeController.getFees(customerIds)

        //then
        assertEquals(OK, response.statusCode)
        assertEquals("4", response.body!![0].customerId)
        assertEquals(6, response.body!![0].transactionCount)
        assertEquals("51,48", response.body!![0].totalFee)
    }
}