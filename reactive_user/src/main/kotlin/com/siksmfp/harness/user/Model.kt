package com.siksmfp.harness.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class UserDao(
    val id: Long,
    val username: String,
    val password: String,
    val age: Int
)


@Document("user")
data class UserMongo(
    @Id
    val id: String,
    val username: String,
    val password: String,
    val age: Int
)
