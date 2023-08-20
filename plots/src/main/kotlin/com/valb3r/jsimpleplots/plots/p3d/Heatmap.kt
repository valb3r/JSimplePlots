package com.valb3r.jsimpleplots.plots.p3d

import com.valb3r.jsimpleplots.plots.p2d.InternalPlot2d
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS
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

/**
 * Pseudo-2d plot - heatmap of 3d function.
 */
class Heatmap: Plot3d<Heatmap>(name = "Heatmap") {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray
    private lateinit var z: FloatArray

    /**
     * X variable.
     */
    fun x(x: FloatArray): Heatmap {
        this.x = x
        return this
    }

    /**
     * Y variable.
     */
    fun y(y: FloatArray): Heatmap {
        this.y = y
        return this
    }

    /**
     * Z variable.
     */
    fun z(z: FloatArray): Heatmap {
        this.z = z
        return this
    }

    /**
     * X variable.
     */
    fun x(x: DoubleArray): Heatmap {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Y variable.
     */
    fun y(y: DoubleArray): Heatmap {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Z variable.
     */
    fun z(z: DoubleArray): Heatmap {
        this.z = z.map { it.toFloat() }.toFloatArray()
        return this
    }


    /**
     * X-Y-Z tuple input in one array
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

    /**
     * Open plot in new Swing window.
     */
    fun plot(): Heatmap {
        val chart = awtChart()
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
        return this
    }

    private fun legend(): List<Legend> {
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(this.name, COLORS[0]))
        return infos
    }

    private fun awtChart(offscreen: Offscreen3d? = null): AWTChart {
        val f = chartFactory3d(offscreen)
        // TODO: Assertion/truncation so X.size == Y.size == Z.size
        val surface: Shape = SurfaceBuilder().delaunay(
            x.mapIndexed { ind, xp -> Coord3d(xp, y[ind], z[ind]) }
        )
        surface.isWireframeDisplayed = wireframe
        surface.colorMapper = ColorMapper(
            ColorMapRainbow(),
            z.min().toDouble(),
            z.max().toDouble()
        )

        val chart: AWTChart = f.newChart(Quality.Advanced()) as AWTChart
        chart.add(surface)
        return chart
    }

    override fun internalRepresentation(offscreen: Offscreen3d?): InternalPlot2d {
        return object : InternalPlot2d {
            override val chart: Chart
                get() = awtChart(offscreen)
            override val legend: List<Legend>
                get() = legend()
        }
    }
}