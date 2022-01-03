package com.siksmfp.harness.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
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
            GET("$USER_PATH/{id}", handler::findById)
            POST(USER_PATH, handler::save)
            DELETE("$USER_PATH/{id}", handler::deleteById)
        }
    }
}

@Component
class ReactiveHandler(
    private val service: ReactiveService
) {

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id")
        return service.findUserById(id)
            .flatMap { ServerResponse.ok().body(fromValue(it)) }
            .switchIfEmpty(
                ServerResponse.notFound().build()
            )
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.accepted().build()
    }

    fun save(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(UserDao::class.java)
            .flatMap(service::saveUser)
            .flatMap {
                ServerResponse.ok()
                    .body(fromValue(it))
            }
    }
}