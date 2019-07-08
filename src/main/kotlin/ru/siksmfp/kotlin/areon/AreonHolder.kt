package ru.siksmfp.kotlin.areon

import io.aeron.Aeron
import io.aeron.driver.MediaDriver

object AreonHolder {

    private val driver = MediaDriver.launchEmbedded()
    private val context = Aeron.Context();
    val aeron = Aeron.connect(context);

    init {
        context.aeronDirectoryName(driver!!.aeronDirectoryName())
    }

}