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

private const val LINEAR_OF_Y = "Linear of Y"

class Linear: Plot2d() {
    private lateinit var y: FloatArray
    private var name = LINEAR_OF_Y

    fun y(y: FloatArray): Linear {
        this.y = y
        return this
    }

    fun y(y: DoubleArray): Linear {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    fun named(name: String): Linear {
        this.name = name
        return this
    }

    fun plot(): Linear {
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

        val serie = LineSerie2d(name)
        y.forEachIndexed { ind, yp -> serie.add(ind.toFloat(), yp)}
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