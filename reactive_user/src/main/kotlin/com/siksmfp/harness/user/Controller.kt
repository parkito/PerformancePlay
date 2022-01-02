package com.siksmfp.harness.user

import com.siksmfp.harness.user.Router.Companion.USER_PATH
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromProducer
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import java.net.URI

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
            POST("/$USER_PATH", handler::save)
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
            fromProducer(service.findUserById(id), UserDao::class.java)
        )
    }

    fun save(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(UserDao::class.java)
            .flatMap { service.saveUser(it) }
            .flatMap {
                ServerResponse.created(URI.create("/$USER_PATH/${it.id}")).build()
            }
    }
}