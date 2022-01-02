package com.siksmfp.harness.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class UserDao(
    val id: String? = null,
    val username: String,
    val password: String,
    val age: Int
)


@Document("user")
data class UserMongo(
    @Id
    val id: String? = null,
    val username: String,
    val password: String,
    val age: Int
)
