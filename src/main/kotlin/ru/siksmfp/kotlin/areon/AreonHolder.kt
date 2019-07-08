package ru.siksmfp.kotlin.areon

import io.aeron.Aeron
import io.aeron.driver.MediaDriver
import io.aeron.samples.SamplesUtil
import java.util.concurrent.atomic.AtomicBoolean

object AreonHolder {

    private val driver = MediaDriver.launchEmbedded()
    private val context = Aeron.Context().aeronDirectoryName(driver.aeronDirectoryName());

    val aeron = Aeron.connect(context);
    const val DRIVER_UDP_ADDRESS = "aeron:udp?endpoint=localhost:40123"
    const val STREAM_ID = 10

    val running = AtomicBoolean(true)
}