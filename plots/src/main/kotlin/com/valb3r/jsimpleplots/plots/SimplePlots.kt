package com.valb3r.jsimpleplots.plots

import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.chart.factories.AWTChartFactory
import org.jzy3d.chart.factories.SwingChartFactory
import org.jzy3d.colors.Color
import org.jzy3d.colors.ColorMapper
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot2d.primitives.LineSerie2d
import org.jzy3d.plot3d.builder.SurfaceBuilder
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font
import kotlin.math.*
import kotlin.properties.Delegates

private val COLORS = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE)

object SimplePlots {

    fun distributionHistogram(): DistributionHistogram {
        return DistributionHistogram()
    }

    fun linear(): Linear {
        return Linear()
    }

    fun xy(): XY {
        return XY()
    }

    fun heatmap(): Heatmap {
        return Heatmap()
    }

    fun surface(): Surface {
        return Surface()
    }

    fun fft(): Fft {
        return Fft()
    }

    fun waterfallFft(): WaterfallFft {
        return WaterfallFft()
    }
}

class Fft {
    private var samplingFrequency by Delegates.notNull<Float>()
    private lateinit var y: FloatArray

    fun y(y: FloatArray): Fft {
        this.y = y
        return this
    }

    fun y(y: DoubleArray): Fft {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun samplingFrequency(samplingFrequency: Float): Fft {
        this.samplingFrequency = samplingFrequency
        return this
    }

    fun plot(): Fft {
        val color = COLORS[0]
        val name = "FFT (Amplitude) of Y"

        val f = SwingChartFactory()
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
        chart.add(listOf(serie))

        // Legend
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(name, color))
        val legend = OverlayLegendRenderer(infos)
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font("Helvetica", Font.PLAIN, 12)
        chart.addRenderer(legend)
        chart.axisLayout.font = org.jzy3d.painters.Font("Helvetica", 30)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }
}

class WaterfallFft {
    private var samplingFrequency by Delegates.notNull<Float>()
    private var waterfallChunk by Delegates.notNull<Int>()
    private lateinit var y: FloatArray
    private var wireframe = false

    fun y(y: FloatArray): WaterfallFft {
        this.y = y
        return this
    }

    fun y(y: DoubleArray): WaterfallFft {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun samplingFrequency(samplingFrequency: Float): WaterfallFft {
        this.samplingFrequency = samplingFrequency
        return this
    }

    fun chunkSize(waterfallChunk: Int): WaterfallFft {
        this.waterfallChunk = waterfallChunk
        return this
    }

    fun wireframe(wireframe: Boolean): WaterfallFft {
        this.wireframe = wireframe
        return this
    }

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

class DistributionHistogram {
    private lateinit var y: FloatArray

    fun y(y: FloatArray): DistributionHistogram {
        this.y = y
        return this
    }

    fun y(y: DoubleArray): DistributionHistogram {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun plot(): DistributionHistogram {
        val color = COLORS[0]
        val name = "Distribution of X"

        val f = SwingChartFactory()
        val chart = f.newChart() as AWTChart

        val binCount = (1.0f + 3.332f * log10(y.size.toFloat())).toInt() // Sturges' rule
        val start = y.min()
        val binSize = (y.max() - start) / binCount
        val histogram = FloatArray(binCount)
        y.forEach {
            val ind = ((it - start) / binSize).toInt()
            histogram[min(ind, histogram.size - 1)]++
        }

        val serie = LineSerie2d(name)
        histogram.indices.forEach {
            serie.add(start + it * binSize, histogram[it])
            serie.add(start + (it + 1) * binSize, histogram[it])
        }
        serie.color = color
        chart.add(listOf(serie))

        // Legend
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(name, color))
        val legend = OverlayLegendRenderer(infos)
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font("Helvetica", Font.PLAIN, 12)
        chart.addRenderer(legend)
        chart.axisLayout.font = org.jzy3d.painters.Font("Helvetica", 30)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }
}

class Linear {
    private lateinit var y: FloatArray

    fun y(y: FloatArray): Linear {
        this.y = y
        return this
    }

    fun y(y: DoubleArray): Linear {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun plot(): Linear {
        val color = COLORS[0]
        val name = "Linear of Y"

        val f = SwingChartFactory()
        val chart = f.newChart() as AWTChart

        val serie = LineSerie2d(name)
        y.forEachIndexed { ind, yp -> serie.add(ind.toFloat(), yp)}
        serie.color = color
        chart.add(listOf(serie))

        // Legend
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(name, color))
        val legend = OverlayLegendRenderer(infos)
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font("Helvetica", Font.PLAIN, 12)
        chart.addRenderer(legend)
        chart.axisLayout.font = org.jzy3d.painters.Font("Helvetica", 30)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }
}

class XY {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray

    fun x(x: FloatArray): XY {
        this.x = x
        return this
    }

    fun y(y: FloatArray): XY {
        this.y = y
        return this
    }

    fun x(x: DoubleArray): XY {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun y(y: DoubleArray): XY {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Format is [x0,y0, x1,y1 ...]
     */
    fun xy(xy: FloatArray): XY {
        val size = xy.size / 2
        this.x = FloatArray(size)
        this.y = FloatArray(size)

        var pos = 0
        xy.forEachIndexed {ind, value ->
            if (0 == ind % 2) {
                this.x[pos] = value
            } else {
                this.y[pos] = value
                pos++
            }
        }
        return this
    }

    fun plot(): XY {
        val color = COLORS[0]
        val name = "X-Y"

        val f = SwingChartFactory()
        val chart = f.newChart() as AWTChart

        val serie = LineSerie2d(name)
        // TODO: Assertion/truncation so X.size == Y.size
        x.forEachIndexed { ind, xp -> serie.add(xp, y[ind])}
        serie.color = color
        chart.add(listOf(serie))

        // Legend
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(name, color))
        val legend = OverlayLegendRenderer(infos)
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font("Helvetica", Font.PLAIN, 12)
        chart.addRenderer(legend)
        chart.axisLayout.font = org.jzy3d.painters.Font("Helvetica", 30)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }
}

class Heatmap {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray
    private lateinit var z: FloatArray
    private var wireframe = false

    fun x(x: FloatArray): Heatmap {
        this.x = x
        return this
    }

    fun y(y: FloatArray): Heatmap {
        this.y = y
        return this
    }

    fun z(z: FloatArray): Heatmap {
        this.z = z
        return this
    }

    fun x(x: DoubleArray): Heatmap {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun y(y: DoubleArray): Heatmap {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun z(z: DoubleArray): Heatmap {
        this.z = z.map { it.toFloat() }.toFloatArray()
        return this
    }


    /**
     * Format is [x0,y0,z0, x1,y1,z1 ...]
     */
    fun xyz(xyz: FloatArray): Heatmap {
        val size = xyz.size / 3
        this.x = FloatArray(size)
        this.y = FloatArray(size)
        this.z = FloatArray(size)

        var pos = 0
        xyz.forEachIndexed {ind, value ->
            when (ind % 3) {
                0 -> this.x[pos] = value
                1 -> this.y[pos] = value
                else -> {this.z[pos] = value; pos++}
            }
        }
        return this
    }

    fun wireframe(wireframe: Boolean): Heatmap {
        this.wireframe = wireframe
        return this
    }

    fun plot(): Heatmap {
        // TODO: Assertion/truncation so X.size == Y.size == Z.size
        val surface: Shape = SurfaceBuilder().delaunay(
            x.mapIndexed { ind, xp -> Coord3d(xp, y[ind], z[ind])}
        )
        surface.isWireframeDisplayed = wireframe
        surface.colorMapper = ColorMapper(
            ColorMapRainbow(),
            z.min().toDouble(),
            z.max().toDouble()
        )

        val chart: Chart = AWTChartFactory().newChart(Quality.Advanced())
        chart.add(surface)
        chart.view2d()
        chart.open()
        chart.addMouse()

        return this
    }
}

class Surface {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray
    private lateinit var z: FloatArray
    private var wireframe = false

    fun x(x: FloatArray): Surface {
        this.x = x
        return this
    }

    fun y(y: FloatArray): Surface {
        this.y = y
        return this
    }

    fun z(z: FloatArray): Surface {
        this.z = z
        return this
    }

    fun x(x: DoubleArray): Surface {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun y(y: DoubleArray): Surface {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun z(z: DoubleArray): Surface {
        this.z = z.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun wireframe(wireframe: Boolean): Surface {
        this.wireframe = wireframe
        return this
    }

    /**
     * Format is [x0,y0,z0, x1,y1,z1 ...]
     */
    fun xyz(xyz: FloatArray): Surface {
        val size = xyz.size / 3
        this.x = FloatArray(size)
        this.y = FloatArray(size)
        this.z = FloatArray(size)

        var pos = 0
        xyz.forEachIndexed {ind, value ->
            when (ind % 3) {
                0 -> this.x[pos] = value
                1 -> this.y[pos] = value
                else -> {this.z[pos] = value; pos++}
            }
        }

        return this
    }

    fun plot(): Surface {
        // TODO: Assertion/truncation so X.size == Y.size == Z.size
        val surface: Shape = SurfaceBuilder().delaunay(
            x.mapIndexed { ind, xp -> Coord3d(xp, y[ind], z[ind])}
        )
        surface.isWireframeDisplayed = wireframe
        surface.colorMapper = ColorMapper(
            ColorMapRainbow(),
            z.min().toDouble(),
            z.max().toDouble()
        )

        val chart: Chart = AWTChartFactory().newChart(Quality.Advanced())
        chart.add(surface)
        chart.view3d()
        chart.open()
        chart.addMouse()

        return this

    }
}

fun FloatArray.log10(): FloatArray {
    return this.map {
        val value = log10(it)
        return@map if (value.isFinite()) value else 0.0f
    }.toFloatArray()
}