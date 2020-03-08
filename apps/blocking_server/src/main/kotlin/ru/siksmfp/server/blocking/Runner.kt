package ru.siksmfp.server.blocking

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.siksmfp.server.blocking.model.Log
import ru.siksmfp.server.blocking.repository.LogRepository
import javax.annotation.PostConstruct

@Component
class Runner(
        @Autowired
        private val logRepository: LogRepository
) {
    companion object {
        var log = LoggerFactory.getLogger(Runner::class.java)
    }

    @PostConstruct
    fun init() {
        log.info("HELLO")
        val log = Log(client = "client", operation = "POST", entityId = 1)
        logRepository.save(log)
    }
}