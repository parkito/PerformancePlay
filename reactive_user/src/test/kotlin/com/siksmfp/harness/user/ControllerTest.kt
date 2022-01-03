package com.siksmfp.harness.user

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class ControllerTest : IntegrationParent() {

    @Test
    fun one() {
//        client
//            .get().uri("$API/$USER_PATH/1")
//            .accept(APPLICATION_JSON)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(UserDao::class.java)
//            .value {
//                Assertions.assertEquals(1, it.id)
//            }
    }

    @Test
    fun saveOne() {
        StepVerifier.create(
            client.create(
                UserDao(null, "name1", "pass", 10)
            )
        ).consumeNextWith {
            assertNotNull(it)
            assertTrue(it!!.isNotEmpty())
        }.verifyComplete()
    }
}