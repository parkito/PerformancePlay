package com.siksmfp.harness.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound
import reactor.test.StepVerifier
import java.util.concurrent.atomic.AtomicReference

class ControllerTest : IntegrationParent() {

    @SpyBean
    private lateinit var repository: ReactiveRepository

    @Test
    fun saveOne() {
        val createMono = client.create(
            UserDao(null, "name1", "pass1", 10)
        )

        StepVerifier.create(createMono)
            .assertNext {
                assertNotNull(it)
                assertTrue(it!!.isNotEmpty())
            }.verifyComplete()
    }

    @Test
    fun saveOneErred() {
        val createMono = client.create(
            UserDao(null, "name1", "pass1", 10)
        )

        Mockito.`when`(repository.findById(Mockito.anyString()))
    }

    @Test
    fun saveAndRetrieveOne() {
        val toSave = UserDao(null, "name1", "pass", 10)
        val action = client.create(toSave)
            .flatMap {
                client.find(it)
            }

        StepVerifier.create(action)
            .assertNext {
                assertEquals(it.username, toSave.username)
                assertEquals(it.password, toSave.password)
                assertEquals(it.age, toSave.age)
            }.verifyComplete()
    }

    @Test
    fun saveAndDelete() {
        val toSave = UserDao(null, "name1", "pass", 10)
        val create = client.create(toSave)
            .doOnError { fail("Create: $it") }

        val id = AtomicReference("")
        val find = create.flatMap {
            client.find(it)
        }.doOnNext {
            assertEquals(it.username, toSave.username)
            assertEquals(it.password, toSave.password)
            assertEquals(it.age, toSave.age)
            id.set(it.id)
        }.doOnError {
            fail("Find: $it")
        }

        val delete = find.flatMap {
            client.delete(it.id!!)
        }.doOnError { fail("Delete: $it") }

        val findAgain = client.find(id.get())

        StepVerifier.create(delete).verifyComplete()
        StepVerifier.create(findAgain)
            .consumeErrorWith {
                assertTrue(it is NotFound)
                it as NotFound
                assertEquals(NOT_FOUND, it.statusCode)
            }.verify()
    }
}