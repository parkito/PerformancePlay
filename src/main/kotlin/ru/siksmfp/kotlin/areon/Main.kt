package ru.siksmfp.kotlin.areon

import java.util.concurrent.TimeUnit

open class Main

fun main(args: Array<String>) {
    val subscriber = Subscriber()
    val publisher = Publisher()

    subscriber.start()

    var count = 0
    while (true) {
        val message = "Hello ${count++}"
        publisher.publish(message)
        println("Published message $message")
        TimeUnit.SECONDS.sleep(2)
    }
}