package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.colors.Color
import org.jzy3d.plot2d.primitives.LineSerie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font
import kotlin.math.log10
import kotlin.math.min

private const val DISTRIBUTION_OF_X = "Distribution of X"

/**
 * Plots distribution histogram of the variable.
 */
class DistributionHistogram: Plot2d<DistributionHistogram>(DISTRIBUTION_OF_X) {
    private lateinit var y: FloatArray

    /**
     * Input variable for histogram.
     */
    fun y(y: FloatArray): DistributionHistogram {
        this.y = y
        return this
    }

    /**
     * Input variable for histogram.
     */
    fun y(y: DoubleArray): DistributionHistogram {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): UpdatablePlot2d<DistributionHistogram> {
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