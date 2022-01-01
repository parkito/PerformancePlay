package com.siksmfp.harness.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class Router(
) {
    companion object {
        const val API = "/api"
        const val USER_PATH = "user"
    }

    @Bean
    fun webRouter(handler: ReactiveHandler) = router {
        API.nest {
            GET("/$USER_PATH/{id}", handler::findById)
        }
    }
}

@Component
class ReactiveHandler(
    private val service: ReactiveService
) {

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id")
        return ServerResponse.ok().body(
            BodyInserters.fromProducer(Mono.just(UserDao(1, "name", "pass", 25)), UserDao::class.java)
        )
    }

    fun save(req: ServerRequest): Mono<ServerResponse> {

    }
}