package ru.siksmfp.server.blocking.performance

import org.jsmart.zerocode.core.domain.JsonTestCase
import org.jsmart.zerocode.core.domain.TargetEnv
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner
import org.junit.Test
import org.junit.runner.RunWith

@TargetEnv("test_target.properties")
@RunWith(ZeroCodeUnitRunner::class)
class MessageControllerTest {

    @Test
    @JsonTestCase("case/create_user_case.json")
    @Throws(Exception::class)
    fun testGetScreeningLocalAndGlobal() {
    }
}