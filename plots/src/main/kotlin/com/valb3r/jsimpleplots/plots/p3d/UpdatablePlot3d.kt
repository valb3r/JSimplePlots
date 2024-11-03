package com.valb3r.jsimpleplots.plots.p3d

import org.jzy3d.chart.Chart
import org.jzy3d.plot3d.primitives.Shape

class UpdatablePlot3d<T: IPlot3d<T>>(
    private val base: IPlot3d<T>,
    private val chart: Chart,
    private val shape: Shape
): IPlot3d<T> by base {

    fun addPt(x: Float, y: Float, z: Float, updateBounds: Boolean = true) {
        throw IllegalStateException("Not implemented yet")
    }

    fun addPt(x: Double, y: Double, updateBounds: Boolean = true) {
        throw IllegalStateException("Not implemented yet")
    }
}