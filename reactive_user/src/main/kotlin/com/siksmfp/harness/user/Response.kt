package com.siksmfp.harness.user

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime

class NotFoundException(
    private val entity: String,
    private val id: String
) : RuntimeException("Entity: $entity with id: $id is not found")

class ServiceException(
    private val msg: String
) : RuntimeException(msg)

class ValidationException(
    private val msg: String
) : RuntimeException(msg)


class ErredResponse(
    val reason: String,
    val localDateTime: LocalDateTime = LocalDateTime.now()
)

fun <T> ok(res: T): Mono<ServerResponse> = ServerResponse.ok().body(fromValue(res))

fun ok(): Mono<ServerResponse> = ServerResponse.ok().build()

fun notFound(err: ErredResponse): Mono<ServerResponse> = toResponse(err, HttpStatus.NOT_FOUND)

fun validationError(err: ErredResponse): Mono<ServerResponse> = toResponse(err, BAD_REQUEST)

fun toResponse(err: ErredResponse, status: HttpStatus): Mono<ServerResponse> =
    ServerResponse.status(status)
        .body(fromValue(err))