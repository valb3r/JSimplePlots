package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS
import org.jzy3d.plot2d.primitives.Serie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font

/**
 * Plots multiple charts in one window.
 */
class Multiple2d: Plot2d<Multiple2d>("") {
    private var plots = mutableListOf<Plot2d<*>>()
    private var keepColors = false

    /**
     * Add plot to multiple plots chart.
     */
    fun add(plot2d: Plot2d<*>): Multiple2d {
        plots += plot2d
        return this
    }

    /**
     * Preserve original plot colors. If false - will assign colors automatically.
     */
    fun keepColors(keepColors: Boolean): Multiple2d {
        this.keepColors = keepColors
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): Multiple2d {
        val series = mutableListOf<Serie2d>()
        val legends = mutableListOf<Legend>()
        var index = 0
        plots.forEach { plot ->
            val internalRepresentation = plot.internalRepresentation()
            internalRepresentation.legend.forEach { legend ->
                val serie = internalRepresentation.chart.getSerie(legend.label, Serie2d.Type.LINE)
                if (!keepColors) {
                    val color = COLORS[index % COLORS.size]
                    serie.color = color
                    legend.color = color
                }
                series += serie
                legends += legend
                ++index
            }
        }

        val chart = chartFactory2d().newChart() as AWTChart
        series.forEach { chart.add(it) }
        // Legend
        val legend = OverlayLegendRenderer(legends)
        val layout: LineLegendLayout = legend.layout
        layout.backgroundColor = Color.WHITE
        layout.font = Font(fontFace, Font.PLAIN, fontSize)
        chart.addRenderer(legend)
        enableMouse(chart)
        chart.axisLayout.font = org.jzy3d.painters.Font(fontFace, axisFontSize)

        // Open as 2D chart
        chart.view2d()
        chart.open()
        return this
    }

    override fun internalRepresentation(offscreen: Offscreen2d?): InternalPlot2d {
        TODO("Not yet implemented")
    }
}