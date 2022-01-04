package com.siksmfp.harness.user

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux


interface ReactiveRepository : ReactiveMongoRepository<UserMongo, String> {
    fun findAllBy(pageable: Pageable): Flux<UserMongo>
}