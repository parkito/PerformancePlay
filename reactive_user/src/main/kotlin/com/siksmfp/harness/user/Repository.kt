package com.siksmfp.harness.user

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ReactiveRepository : ReactiveMongoRepository<UserMongo, String>