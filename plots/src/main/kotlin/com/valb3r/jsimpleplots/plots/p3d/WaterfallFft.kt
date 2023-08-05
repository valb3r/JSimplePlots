package com.valb3r.jsimpleplots.plots.p3d

import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import org.jzy3d.chart.Chart
import org.jzy3d.chart.factories.AWTChartFactory
import org.jzy3d.colors.ColorMapper
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.builder.SurfaceBuilder
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality
import kotlin.math.log2
import kotlin.math.min
import kotlin.math.pow
import kotlin.properties.Delegates

/**
 * Fast fourier amplitude transform in form of waterfall (time-frequency domain)
 */
class WaterfallFft {
    private var samplingFrequency by Delegates.notNull<Float>()
    private var waterfallChunk by Delegates.notNull<Int>()
    private lateinit var y: FloatArray
    private var wireframe = false

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
     * Show wireframe.
     */
    fun wireframe(wireframe: Boolean): WaterfallFft {
        this.wireframe = wireframe
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): WaterfallFft {
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

        // Legend
        val surface: Shape = SurfaceBuilder().delaunay(coords)
        surface.isWireframeDisplayed = wireframe
        surface.colorMapper = ColorMapper(
            ColorMapRainbow(),
            coords.minOfOrNull { it.z }?.toDouble() ?: 0.0,
            coords.maxOfOrNull { it.z }?.toDouble() ?: 0.0
        )

        val chart: Chart = AWTChartFactory().newChart(Quality.Advanced())
        chart.add(surface)
        chart.view2d()
        chart.open()
        chart.addMouse()

        return this
    }
}