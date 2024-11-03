package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.plot2d.primitives.LineSerie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font

private const val XY_NAME = "X-Y"

/**
 * X-Y plot
 */
class XY: Plot2d<XY>(XY_NAME) {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray

    /**
     * X variable (abscissa)
     */
    fun x(x: FloatArray): XY {
        this.x = x
        return this
    }

    /**
     * Y variable (ordinate)
     */
    fun y(y: FloatArray): XY {
        this.y = y
        return this
    }

    /**
     * X variable (abscissa)
     */
    fun x(x: DoubleArray): XY {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Y variable (ordinate)
     */
    fun y(y: DoubleArray): XY {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * X-Y tuple input in one array
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

    /**
     * Open plot in new Swing window.
     */
    fun plot(): UpdatablePlot2d<XY> {
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

        val serie = LineSerie2d(this.name)
        // TODO: Assertion/truncation so X.size == Y.size
        x.forEachIndexed { ind, xp -> serie.add(xp, y[ind]) }
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