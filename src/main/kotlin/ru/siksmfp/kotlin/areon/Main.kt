package ru.siksmfp.kotlin.areon

import java.util.concurrent.TimeUnit

open class Main

fun main(args: Array<String>) {
    val subscriber = Subscriber()
    val publisher = Publisher()

    subscriber.start()
    while (true) {
        println("Publishing")
        publisher.publish("Hello")
        TimeUnit.SECONDS.sleep(2)
    }
}