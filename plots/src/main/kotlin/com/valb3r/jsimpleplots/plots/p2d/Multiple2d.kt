package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS
import org.jzy3d.plot2d.primitives.Serie2d
import org.jzy3d.plot3d.rendering.legends.overlay.Legend
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer
import java.awt.Font

class Multiple2d: Plot2d() {
    private var plots = mutableListOf<Plot2d>()

    fun add(plot2d: Plot2d): Multiple2d {
        plots += plot2d
        return this
    }

    fun plot(): Multiple2d {
        val series = mutableListOf<Serie2d>()
        val legends = mutableListOf<Legend>()
        var index = 0
        plots.forEach { plot ->
            val internalRepresentation = plot.internalRepresentation()
            internalRepresentation.legend.forEach { legend ->
                val serie = internalRepresentation.chart.getSerie(legend.label, Serie2d.Type.LINE)
                val color = COLORS[index % COLORS.size]
                serie.color = color
                legend.color = color
                series += serie
                legends += legend
                ++index
            }
        }

        val chart = swingChartFactory2d().newChart()
        series.forEach { chart.add(it) }
        // Legend
        val legend = OverlayLegendRenderer(legends)
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

    override fun internalRepresentation(): InternalPlot2d {
        TODO("Not yet implemented")
    }
}