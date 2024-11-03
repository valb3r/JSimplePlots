package com.valb3r.jsimpleplots.plots.p3d

import com.valb3r.jsimpleplots.plots.p2d.InternalPlot2d
import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.colors.ColorMapper
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.builder.SurfaceBuilder
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font
import kotlin.math.log2
import kotlin.math.min
import kotlin.math.pow
import kotlin.properties.Delegates

/**
 * Fast fourier amplitude transform in form of waterfall (time-frequency domain)
 */
class WaterfallFft: Plot3d<WaterfallFft>("Waterfall") {
    private var samplingFrequency by Delegates.notNull<Float>()
    private var waterfallChunk by Delegates.notNull<Int>()
    private lateinit var y: FloatArray

    /**
     * Input variable.
     */
    fun y(y: FloatArray): WaterfallFft {
        this.y = y
        return this
    }

    /**
     * Input variable.
     */
    fun y(y: DoubleArray): WaterfallFft {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Sampling frequency, Hz
     */
    fun samplingFrequency(samplingFrequency: Float): WaterfallFft {
        this.samplingFrequency = samplingFrequency
        return this
    }

    /**
     * Length of FFT transformation or width of the plot (X axis) in input array point count.
     */
    fun chunkSize(waterfallChunk: Int): WaterfallFft {
        this.waterfallChunk = waterfallChunk
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): UpdatablePlot3d<WaterfallFft> {
        val (chart, shape) = awtChart()
        // Legend
        val legend = OverlayLegendRenderer(legend())
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font(fontFace, Font.PLAIN, fontSize)
        chart.addRenderer(legend)
        com.valb3r.jsimpleplots.plots.p2d.enableMouse(chart)
        chart.axisLayout.font = org.jzy3d.painters.Font(fontFace, axisFontSize)

        chart.open()
        chart.view2d()
        chart.addMouse()
        return UpdatablePlot3d(this, chart, shape)
    }

    private fun legend(): List<Legend> {
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(this.name, Color.COLORS[0]))
        return infos
    }

    private fun awtChart(offscreen: Offscreen3d? = null): ChartAndShape<AWTChart> {
        val f = chartFactory3d(offscreen)
        val coords = mutableListOf<Coord3d>()
        var rowIndex = 0
        while (rowIndex < y.size) {
            val transform = FastFourierTransformer(DftNormalization.STANDARD)
            val size = min(y.size - rowIndex, waterfallChunk)
            val paddedY = DoubleArray(2.0.pow((log2(waterfallChunk.toDouble()) + 1).toInt()).toInt())
            y.sliceArray(rowIndex..< rowIndex + size).forEachIndexed { index, value -> paddedY[index] = value.toDouble() }
            val fft = transform.transform(paddedY, TransformType.FORWARD)
            fft.take(fft.size / 2).forEachIndexed { ind, value ->
                coords += Coord3d(
                    (ind * this.samplingFrequency / fft.size).toDouble(),
                    rowIndex / waterfallChunk.toDouble(),
                    value.abs() * 2.0f / fft.size
                )
            }
            rowIndex += waterfallChunk
        }

        val surface: Shape = SurfaceBuilder().delaunay(coords)
        surface.isWireframeDisplayed = wireframe
        surface.colorMapper = ColorMapper(
            ColorMapRainbow(),
            coords.minOfOrNull { it.z }?.toDouble() ?: 0.0,
            coords.maxOfOrNull { it.z }?.toDouble() ?: 0.0
        )

        val chart: AWTChart = f.newChart(Quality.Advanced()) as AWTChart
        chart.add(surface)
        return ChartAndShape(chart, surface)
    }

    override fun internalRepresentation(offscreen: Offscreen3d?): InternalPlot2d {
        return object : InternalPlot2d {
            override val chart: Chart
                get() = awtChart(offscreen).chart
            override val legend: List<Legend>
                get() = legend()
        }
    }
}