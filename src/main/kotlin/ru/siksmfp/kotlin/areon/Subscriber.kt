package ru.siksmfp.kotlin.areon

import io.aeron.Subscription
import io.aeron.samples.SamplesUtil.printStringMessage

class Subscriber {
    private val subscription: Subscription = AreonHolder.aeron.addSubscription("aeron:udp?endpoint=localhost:40123", 10)

    fun start() {
        while (true) {
            try {
                subscription.poll(printStringMessage(10), 10)
            } catch (ex: Exception) {
            }

        }
    }

}
