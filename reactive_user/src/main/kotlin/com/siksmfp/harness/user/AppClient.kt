package com.siksmfp.harness.user

import com.siksmfp.harness.user.Router.Companion.USER_PATH
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class AppClient(
    private val client: WebClient,
) {

    fun create(userDao: UserDao): Mono<String> {
        return client.post()
            .uri(USER_PATH)
            .body(fromValue(userDao))
            .retrieve()
            .toEntity(UserDao::class.java)
            .mapNotNull { it.body?.id }
    }

    fun find(id: String): Mono<UserDao> {
        return client.get()
            .uri {
                it.path(USER_PATH)
                    .pathSegment(id)
                    .build()
            }
            .retrieve()
            .toEntity(UserDao::class.java)
            .mapNotNull { it.body }
    }

    fun delete(id: String): Mono<Void> {
        return client.delete()
            .uri {
                it.path(USER_PATH)
                    .pathSegment(id)
                    .build()
            }
            .retrieve()
            .toEntity(Void::class.java)
            .mapNotNull { it.body }
    }
}