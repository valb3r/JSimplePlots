import com.valb3r.jsimpleplots.plots.SimplePlots
import kotlin.math.PI
import kotlin.math.sin

object Main2d {

    @JvmStatic
    fun main(args: Array<String>) {
        SimplePlots.xy()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
            .plot()
    }
}

object Main2dInplaces {

    @JvmStatic
    fun main(args: Array<String>) {
        SimplePlots.xy()
            .xy(floatArrayOf(1.0f, 1.0f, 2.0f, 4.0f, 3.0f, 9.0f, 4.0f, 16.0f))
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

object MainFft {

    @JvmStatic
    fun main(args: Array<String>) {
        val samplingFrequency = 800.0f
        val data = (0..500).map { 0.4f * sin(2.0 * PI * 150 * it.toDouble() / samplingFrequency).toFloat() }.toFloatArray()
        SimplePlots.fft()
            .y(data)
            .samplingFrequency(samplingFrequency)
            .plot()
    }
}

object MainFftWaterfall {

    @JvmStatic
    fun main(args: Array<String>) {
        val samplingFrequency = 800.0f
        val data = (0..5000).map { 0.4f * sin(2.0 * PI * 150 * it.toDouble() / samplingFrequency).toFloat() }.toFloatArray()
        SimplePlots.waterfallFft()
            .y(data)
            .chunkSize(100)
            .samplingFrequency(samplingFrequency)
            .plot()
    }
}