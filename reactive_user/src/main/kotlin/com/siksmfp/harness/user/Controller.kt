package com.siksmfp.harness.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import kotlin.Int.Companion.MAX_VALUE

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
            GET(USER_PATH, handler::findAll)
            GET("$USER_PATH/counter", handler::count)
            GET("$USER_PATH/{id}", handler::findById)
            POST(USER_PATH, handler::save)
            DELETE("$USER_PATH/{id}", handler::deleteById)
        }
    }
}

@Component
class ReactiveHandler(
    private val service: ReactiveService, private val rValidator: RequestExtractor
) {

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val id = rValidator.validateId(req)
        if (id.isFailed()) {
            return validationError(ErredResponse(id.err!!))
        }
        return service.findById(id.validated)
            .flatMap { ok(it) }
            .switchIfEmpty(
                notFound(ErredResponse("User with id=${id.validated} is not found"))
            )
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        val page = req.pathVariables()["page"]?.toInt() ?: 0
        val size = req.pathVariables()["page"]?.toInt() ?: MAX_VALUE
        return ok(service.findAll(PageRequest.of(page, size)))
    }

    fun count(req: ServerRequest): Mono<ServerResponse> {
        return service.count()
            .flatMap { ok(it) }
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id")
        return service.delete(id).flatMap {
            notFound(ErredResponse("User with id=$id is not found"))
        }.switchIfEmpty(ok())
    }

    fun save(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(UserDao::class.java).flatMap(service::save).flatMap { ok(it) }
    }
}

@Component
class RequestExtractor {
    fun validateId(req: ServerRequest): ValidationResult<String> {
        val id = req.pathVariables()["id"]
        return if (id == null) {
            ValidationResult("id variable is not specified in the request", "")
        } else {
            ValidationResult(null, id)
        }
    }
}

data class ValidationResult<T>(
    val err: String?, val validated: T
) {
    fun isFailed(): Boolean = err != null
}