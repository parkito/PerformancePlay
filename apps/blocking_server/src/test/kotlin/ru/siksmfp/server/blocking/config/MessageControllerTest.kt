package ru.siksmfp.server.blocking.config

import org.jsmart.zerocode.core.domain.JsonTestCase
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(ZeroCodeUnitRunner::class)
class MessageControllerTest {

    @Test
    @JsonTestCase("load_tests/get/get_screening_details_by_custid.json")
    @Throws(Exception::class)
    fun testGetScreeningLocalAndGlobal() {
    }
}