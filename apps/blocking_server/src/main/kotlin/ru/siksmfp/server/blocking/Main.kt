package ru.siksmfp.server.blocking

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.siksmfp.server.blocking.repository.LogRepository

@SpringBootApplication
class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Main>(*args)
        }
    }
}