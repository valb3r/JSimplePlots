package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.Chart
import org.jzy3d.plot2d.primitives.Serie2d

class UpdatablePlot2d<T: IPlot2d<T>>(
    private val base: IPlot2d<T>,
    private val chart: Chart,
    private val serie2d: Serie2d
): IPlot2d<T> by base {

    fun addPt(x: Float, y: Float, updateBounds: Boolean = true) {
        serie2d.add(x, y)
        if (updateBounds) {
            chart.view.updateBounds()
        }
        chart.render()
    }

    fun addPt(x: Double, y: Double, updateBounds: Boolean = true) {
        serie2d.add(x, y)
        if (updateBounds) {
            chart.view.updateBounds()
        }
        chart.render()
    }
}