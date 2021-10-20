package com.siksmfp.harness.counter

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong

@SpringBootApplication
class AppMain

@Service
class AppService {
    private val counter = AtomicLong()

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java);
    }

    fun inc() {
        counter.incrementAndGet()
        log.info("{}", counter.get())
    }

    fun get(): Long {
        val newSingleThreadExecutor = Executors.newSingleThreadExecutor()
        newSingleThreadExecutor.execute({ Thread.sleep(9000) })
        return counter.get()
    }

}

@RestController
class AppRest(
    private val appService: AppService
) {

    @PostMapping("/counter")
    fun inc() {
        return appService.inc()
    }

    @GetMapping("/counter")
    fun getInc(): Long {
        return appService.get()
    }
}

fun main(args: Array<String>) {
    runApplication<AppMain>(*args)
}


