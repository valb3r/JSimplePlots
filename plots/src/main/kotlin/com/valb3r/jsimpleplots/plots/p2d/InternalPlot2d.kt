package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.Chart
import org.jzy3d.plot3d.rendering.legends.overlay.Legend

interface InternalPlot2d {

    val chart: Chart
    val legend: List<Legend>
}