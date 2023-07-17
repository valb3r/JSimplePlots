package com.valb3r.jsimpleplots

import com.valb3r.jsimpleplots.plots.SimplePlots

object Main2d {

    @JvmStatic
    fun main(args: Array<String>) {
        SimplePlots.xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
            .plot()
    }
}

object MainHeatmap {

    @JvmStatic
    fun main(args: Array<String>) {
        SimplePlots.heatmap()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f))
            .z(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f))
            .plot()
    }
}

object MainSurface {

    @JvmStatic
    fun main(args: Array<String>) {
        SimplePlots.surface()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f))
            .z(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f))
            .plot()
    }
}