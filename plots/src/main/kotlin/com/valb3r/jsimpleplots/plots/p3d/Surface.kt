package com.valb3r.jsimpleplots.plots.p3d

import com.valb3r.jsimpleplots.plots.p2d.InternalPlot2d
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

/**
 * Plot 3d surface.
 */
class Surface: Plot3d<Surface>("Surface plot") {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray
    private lateinit var z: FloatArray

    /**
     * X variable.
     */
    fun x(x: FloatArray): Surface {
        this.x = x
        return this
    }

    /**
     * Y variable.
     */
    fun y(y: FloatArray): Surface {
        this.y = y
        return this
    }

    /**
     * Z variable.
     */
    fun z(z: FloatArray): Surface {
        this.z = z
        return this
    }

    /**
     * Z variable.
     */
    fun x(x: DoubleArray): Surface {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Y variable.
     */
    fun y(y: DoubleArray): Surface {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Y variable.
     */
    fun z(z: DoubleArray): Surface {
        this.z = z.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * X-Y-Z tuple input in one array
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

    /**
     * X-Y-Z tuple input in one array
     * Format is [x0,y0,z0, x1,y1,z1 ...]
     */
    fun xyz(xyz: DoubleArray): Surface {
        val size = xyz.size / 3
        this.x = FloatArray(size)
        this.y = FloatArray(size)
        this.z = FloatArray(size)

        var pos = 0
        xyz.forEachIndexed {ind, value ->
            when (ind % 3) {
                0 -> this.x[pos] = value.toFloat()
                1 -> this.y[pos] = value.toFloat()
                else -> {this.z[pos] = value.toFloat(); pos++}
            }
        }

        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): UpdatablePlot3d<Surface> {
        val (chart, shape) = awtChart()
        // Legend
        val legend = OverlayLegendRenderer(legend())
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font(fontFace, Font.PLAIN, fontSize)
        chart.addRenderer(legend)
        chart.axisLayout.font = org.jzy3d.painters.Font(fontFace, axisFontSize)

        chart.open()
        chart.view3d()
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
        val legend = OverlayLegendRenderer(legend())
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font(fontFace, Font.PLAIN, fontSize)
        chart.addRenderer(legend)
        chart.axisLayout.font = org.jzy3d.painters.Font(fontFace, axisFontSize)

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