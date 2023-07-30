package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.chart.controllers.mouse.picking.AWTMousePickingPan2dController
import org.jzy3d.chart.controllers.mouse.picking.IMousePickingController
import org.jzy3d.chart.factories.SwingChartFactory
import org.jzy3d.chart.factories.SwingPainterFactory

abstract class Plot2d {
    internal abstract fun internalRepresentation(): InternalPlot2d
}

internal fun swingChartFactory2d(): SwingChartFactory {
    val f = SwingChartFactory()
    f.painterFactory = object : SwingPainterFactory() {
        override fun newMousePickingController(chart: Chart?, clickWidth: Int): IMousePickingController {
            return AWTMousePickingPan2dController(chart, clickWidth)
        }
    }
    return f
}

internal fun enableMouse(chart: AWTChart) {
    chart.addMouse()
    chart.addMousePickingController(5)
}