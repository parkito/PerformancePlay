package ru.siksmfp.server.blocking.model

data class Log(
        val id: Long = 0,
        val client: String,
        val operation: String,
        val entityId: Long
)