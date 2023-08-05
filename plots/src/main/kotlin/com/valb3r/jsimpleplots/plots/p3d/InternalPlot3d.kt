package com.valb3r.jsimpleplots.plots.p3d

import org.jzy3d.chart.Chart
import org.jzy3d.plot3d.rendering.legends.overlay.Legend

interface InternalPlot3d {

    val chart: Chart
    val legend: List<Legend>
}