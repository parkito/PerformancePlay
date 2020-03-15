package ru.siksmfp.server.blocking.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.siksmfp.server.blocking.model.Log
import ru.siksmfp.server.blocking.repository.LogRepository

@Service
class LogService(
        @Autowired
        private val logRepository: LogRepository
) {

    fun log(client: String, operation: Operation, id: Long) {
        val log = Log(
                id = null,
                client = client,
                operation = operation.name,
                entityId = id
        )

        logRepository.save(log)
    }
}