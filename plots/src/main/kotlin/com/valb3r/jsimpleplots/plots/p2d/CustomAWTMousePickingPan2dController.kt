package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.Chart
import org.jzy3d.chart.controllers.mouse.picking.AWTMousePickingPan2dController
import org.jzy3d.maths.Coord3d

// Custom Controller to resolve jumpy zoom
class CustomAWTMousePickingPan2dController(chart: Chart?, brushSize: Int) :
    AWTMousePickingPan2dController(chart, brushSize) {

    /** */
    override fun zoom(factor: Float) {
        val chart = chart
        val viewBounds = chart.view.bounds
        val newBounds = viewBounds.scale(Coord3d(factor, factor, 1f))
        chart.view.setBoundsManual(newBounds)
        // Problematic section:
        //chart.view.shoot()
        //fireControllerEvent(ControllerType.ZOOM, factor)
    }
}
