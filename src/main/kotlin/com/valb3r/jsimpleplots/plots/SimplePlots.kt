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

private val COLORS = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE)

class SimplePlots {

    companion object Plots {

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

    fun plot(): Surface {
        return this
    }
}