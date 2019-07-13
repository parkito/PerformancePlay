package ru.siksmfp.kotlin.areon

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

open class Main

fun main(args: Array<String>) {
    val subscriber = Subscriber()
    val publisher = Publisher()

    subscriber.start()

    var count = 0
    val executor = Executors.newFixedThreadPool(100)

    while (true) {
        executor.execute {
            val message = "Hello ${count++}"
            publisher.publish(message)
            println("Published message $message")
            TimeUnit.SECONDS.sleep(2)
        }
    }
}