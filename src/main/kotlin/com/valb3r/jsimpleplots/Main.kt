package com.valb3r.jsimpleplots

import com.valb3r.jsimpleplots.plots.SimplePlots

fun main(args: Array<String>) {
    SimplePlots.xy()
        .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
        .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
        .plot()
    Thread.sleep(10_000L)
}