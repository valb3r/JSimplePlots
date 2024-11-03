package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.plot2d.primitives.LineSerie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font

private const val LINEAR_OF_Y = "Linear of Y"

/**
 * Linear plot of the variable.
 */
class Linear: Plot2d<Linear>(LINEAR_OF_Y) {
    private lateinit var y: FloatArray

    /**
     * Input variable to plot.
     */
    fun y(y: FloatArray): Linear {
        this.y = y
        return this
    }

    /**
     * Input variable to plot.
     */
    fun y(y: DoubleArray): Linear {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): UpdatablePlot2d<Linear> {
        val (chart, serie) = awtChart()
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
        return UpdatablePlot2d(this, chart, serie)
    }

    private fun legend(): List<Legend> {
        val infos: MutableList<Legend> = ArrayList()
        infos.add(Legend(this.name, color))
        return infos
    }

    private fun awtChart(offscreen: Offscreen2d? = null): ChartAndSerie<AWTChart> {
        val f = chartFactory2d(offscreen)
        val chart = f.newChart() as AWTChart

        val serie = LineSerie2d(name)
        y.forEachIndexed { ind, yp -> serie.add(ind.toFloat(), yp)}
        serie.color = color
        serie.setWidth(width)
        chart.add(listOf(serie))
        return ChartAndSerie(chart, serie)
    }

    override fun internalRepresentation(offscreen: Offscreen2d?): InternalPlot2d {
        return object : InternalPlot2d {
            override val chart: Chart
                get() = awtChart(offscreen).chart
            override val legend: List<Legend>
                get() = legend()
        }
    }
}