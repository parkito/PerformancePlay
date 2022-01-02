package com.siksmfp.harness.user

import com.siksmfp.harness.user.Router.Companion.API
import com.siksmfp.harness.user.Router.Companion.USER_PATH
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(MongoDbParent::class)
class ControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun one() {
        client
            .get().uri("$API/$USER_PATH/1")
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(UserDao::class.java)
            .value {
                Assertions.assertEquals(1, it.id)
            }
    }

    @Test
    fun saveOne() {
        client.post()
            .uri("$API/$USER_PATH")
            .bodyValue(UserDao(null, "name1", "pass", 10))
            .exchange()
            .expectStatus().is2xxSuccessful

//        client
//            .get().uri("$API/$USER_PATH/1")
//            .accept(APPLICATION_JSON)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(UserDao::class.java)
//            .value {
//                Assertions.assertEquals("name", it.username)
//            }
    }
}