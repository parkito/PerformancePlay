package ru.siksmfp.server.blocking.model

data class Message(
        val id: Long,
        val from: Long,
        val to: Long,
        val message: String
)