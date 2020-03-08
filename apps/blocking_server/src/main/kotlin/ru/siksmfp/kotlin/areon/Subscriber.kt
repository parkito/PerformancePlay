package ru.siksmfp.kotlin.areon

import io.aeron.Subscription
import io.aeron.samples.SamplesUtil.printStringMessage
import ru.siksmfp.kotlin.areon.AreonHolder.STREAM_ID
import java.util.concurrent.Executors

class Subscriber {
    private val subscription: Subscription = AreonHolder.aeron.addSubscription(AreonHolder.DRIVER_UDP_ADDRESS, STREAM_ID)

    fun start() {
        val executor = Executors.newSingleThreadExecutor()
        executor.submit {
            while (true) {
                try {
                    subscription.poll(printStringMessage(STREAM_ID), Int.MAX_VALUE)
                    continue
                } catch (ex: Exception) {
                    print("Subscriber error")
                }
            }
        }
    }
}
