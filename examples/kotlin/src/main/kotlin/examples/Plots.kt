package examples

import com.valb3r.jsimpleplots.plots.SimplePlots
import kotlin.math.PI
import kotlin.math.sin

object Main2d {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-2d-xy
        SimplePlots.xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
            .plot()
        // @example-end
    }
}

object Main2dInplaces {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-2d-xy-array
        SimplePlots.xy()
            .xy(floatArrayOf(1.0f, 1.0f, 2.0f, 4.0f, 3.0f, 9.0f, 4.0f, 16.0f))
            .plot()
        // @example-end
    }
}

object MainHeatmap {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-heatmap
        SimplePlots.heatmap()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f))
            .z(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f))
            .plot()
        // @example-end
    }
}

object MainSurface {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-3d-surface
        SimplePlots.surface()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f))
            .z(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f))
            .plot()
        // @example-end
    }
}

object MainFft {

    @JvmStatic
    fun main(args: Array<String>) {
        val samplingFrequency = 800.0f
        val data = (0..500).map { 0.4f * sin(2.0 * PI * 150 * it.toDouble() / samplingFrequency).toFloat() }.toFloatArray()
        // @example-start:example-fft-2d
        SimplePlots.fft()
            .y(data)
            .samplingFrequency(samplingFrequency)
            .plot()
        // @example-end
    }
}

object MainFftWaterfall {

    @JvmStatic
    fun main(args: Array<String>) {
        val samplingFrequency = 800.0f
        val data = (0..5000).map { 0.4f * sin(2.0 * PI * 150 * it.toDouble() / samplingFrequency).toFloat() }.toFloatArray()
        // @example-start:example-fft-heatmap
        SimplePlots.waterfallFft()
            .y(data)
            .chunkSize(100)
            .samplingFrequency(samplingFrequency)
            .plot()
        // @example-end
    }
}