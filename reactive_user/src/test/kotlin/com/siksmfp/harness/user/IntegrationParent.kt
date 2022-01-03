package com.siksmfp.harness.user

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Import(IntegrationTestConfiguration::class)
class IntegrationParent {
    @Autowired
    protected lateinit var client: AppClient

    @Autowired
    private lateinit var reactiveRepository: ReactiveRepository

    @BeforeEach
    fun before() {
        reactiveRepository.deleteAll().block()
    }
}