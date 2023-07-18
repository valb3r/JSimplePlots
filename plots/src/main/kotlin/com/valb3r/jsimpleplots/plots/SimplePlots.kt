package com.valb3r.jsimpleplots.plots

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
import kotlin.math.log10

private val COLORS = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE)

object SimplePlots {

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
}

class Linear {
    private lateinit var y: FloatArray

    fun y(y: FloatArray): Linear {
        this.y = y
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

    fun plot(): Heatmap {
        // TODO: Assertion/truncation so X.size == Y.size == Z.size
        val surface: Shape = SurfaceBuilder().delaunay(
            x.mapIndexed { ind, xp -> Coord3d(xp, y[ind], z[ind])}
        )
        surface.isWireframeDisplayed = false
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
        surface.isWireframeDisplayed = false
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