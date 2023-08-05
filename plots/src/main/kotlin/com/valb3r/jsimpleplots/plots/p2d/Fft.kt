package com.valb3r.jsimpleplots.plots.p2d

import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.plot2d.primitives.LineSerie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font
import kotlin.math.log2
import kotlin.math.pow
import kotlin.properties.Delegates

private const val FFT_AMPLITUDE_OF_Y = "FFT (Amplitude) of Y"

/**
 * Fast-fourier transform of the variable.
 */
class Fft: Plot2d<Fft>(FFT_AMPLITUDE_OF_Y) {
    private var samplingFrequency by Delegates.notNull<Float>()
    private lateinit var y: FloatArray

    /**
     * Input variable.
     */
    fun y(y: FloatArray): Fft {
        this.y = y
        return this
    }

    /**
     * Input variable.
     */
    fun y(y: DoubleArray): Fft {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Sampling frequency of the input variable, Hz
     */
    fun samplingFrequency(samplingFrequency: Float): Fft {
        this.samplingFrequency = samplingFrequency
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): Fft {
        val chart = awtChart()
        // Legend
        val legend = OverlayLegendRenderer(legend())
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font(fontFace, Font.PLAIN, fontSize)
        chart.addRenderer(legend)
        enableMouse(chart)
        chart.axisLayout.font = org.jzy3d.painters.Font(fontFace, axisFontSize)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }

    private fun legend(): List<Legend> {
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(this.name, color))
        return infos
    }

    private fun awtChart(): AWTChart {
        val f = swingChartFactory2d()
        val chart = f.newChart() as AWTChart

        val transform = FastFourierTransformer(DftNormalization.STANDARD)
        val paddedY = DoubleArray(2.0.pow((log2(y.size.toDouble()) + 1).toInt()).toInt())
        y.forEachIndexed { index, value -> paddedY[index] = value.toDouble() }
        val fft = transform.transform(paddedY, TransformType.FORWARD)

        val serie = LineSerie2d(name)
        fft.take(fft.size / 2).forEachIndexed { ind, value ->
            serie.add((ind * this.samplingFrequency / fft.size).toDouble(), value.abs() * 2.0f / fft.size)
        }
        serie.color = color
        serie.setWidth(width)
        chart.add(listOf(serie))
        return chart
    }

    override fun internalRepresentation(): InternalPlot2d {
        return object : InternalPlot2d {
            override val chart: Chart
                get() = awtChart()
            override val legend: List<Legend>
                get() = legend()
        }
    }
}