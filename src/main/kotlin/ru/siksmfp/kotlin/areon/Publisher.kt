package ru.siksmfp.kotlin.areon

import io.aeron.ConcurrentPublication
import org.agrona.BufferUtil
import org.agrona.concurrent.UnsafeBuffer

class Publisher {
    private val BUFFER = UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64))
    private val publication: ConcurrentPublication = AreonHolder.aeron.addPublication(AreonHolder.DRIVER_UDP_ADDRESS, 10)

    fun publish(message: String) {
        val messageBytes = message.toByteArray()
        BUFFER.putBytes(0, messageBytes)

        publication.offer(BUFFER, 0, messageBytes.size)
    }
}