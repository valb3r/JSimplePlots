package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS
import org.jzy3d.plot2d.primitives.LineSerie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font

private const val XY_NAME = "X-Y"

class XY: Plot2d() {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray
    private var name = XY_NAME

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

    fun named(name: String): XY {
        this.name = name
        return this
    }

    fun plot(): XY {
        val chart = awtChart()
        // Legend
        val legend = OverlayLegendRenderer(legend())
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font("Helvetica", Font.PLAIN, 12)
        chart.addRenderer(legend)
        enableMouse(chart)
        chart.axisLayout.font = org.jzy3d.painters.Font("Helvetica", 30)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }

    private fun legend(): List<Legend> {
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(this.name, COLORS[0]))
        return infos
    }

    private fun awtChart(): AWTChart {
        val f = swingChartFactory2d()
        val chart = f.newChart() as AWTChart

        val serie = LineSerie2d(this.name)
        // TODO: Assertion/truncation so X.size == Y.size
        x.forEachIndexed { ind, xp -> serie.add(xp, y[ind]) }
        serie.color = COLORS[0]
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