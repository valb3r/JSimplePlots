package examples

import com.valb3r.jsimpleplots.plots.SimplePlots
import com.valb3r.jsimpleplots.plots.SimplePlots.xy
import java.io.File
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

internal object DynamicPlots {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-2d-xy-dynamic
        val aPlot = xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
            .plot()

        for (i in 0..9) {
            aPlot.addPt(Math.random() * 10.0, Math.random() * 10.0, true)
            try { Thread.sleep(1000L) } catch (ignored: InterruptedException) { }
        }
        // @example-end
    }
}

object Main2dScreenshot {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-2d-xy-screenshot
        SimplePlots.xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
            .screenshot()
            .saveToFile(File("xy.png"))
        // @example-end
    }
}

object Main2dMultiple {

    @JvmStatic
    fun main(args: Array<String>) {
        // @example-start:example-2d-xy-multiple
        val parabola = SimplePlots.xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f))
            .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f, 25.0f, 36.0f))
            .width(5)
            .named("Parabola")
        val cubic = SimplePlots.xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f))
            .y(floatArrayOf(1.0f, 8.0f, 27.0f, 64.0f, 125.0f, 216.0f))
            .width(5)
            .named("Cubic")

        SimplePlots.multiple()
            .add(parabola)
            .add(cubic)
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
            .z(floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f, 0.0f, 1.0f, 2.0f, 3.0f, 0.0f, 1.0f, 2.0f, 3.0f, 0.0f, 1.0f, 2.0f, 3.0f))
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
        // @example-start:example-2d-fft
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

object MainFftWaterfallScreenshot {

    @JvmStatic
    fun main(args: Array<String>) {
        val samplingFrequency = 800.0f
        val data = (0..5000).map { 0.4f * sin(2.0 * PI * 150 * it.toDouble() / samplingFrequency).toFloat() }.toFloatArray()
        // @example-start:example-fft-heatmap-screenshot
        SimplePlots.waterfallFft()
            .y(data)
            .chunkSize(100)
            .samplingFrequency(samplingFrequency)
            .screenshot()
            .saveToFile(File("waterfall.png"))
        // @example-end
    }
}