package com.valb3r.jsimpleplots.plots.p3d

import org.jzy3d.chart.Chart
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController

class CustomAWTCameraMouseController(chart: Chart): AWTCameraMouseController(chart) {

    override fun zoomX(factor: Float, updateView: Boolean) {
        chart.view.zoomX(factor, updateView)
    }

    override fun zoomY(factor: Float, updateView: Boolean) {
        chart.view.zoomY(factor, updateView)
    }

    override fun zoomZ(factor: Float, updateView: Boolean) {
        chart.view.zoomZ(factor, updateView)
    }
}