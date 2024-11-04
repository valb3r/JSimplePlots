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

class MultiUpdatablePlot2d<T: IPlot2d<T>>(
    private val base: IPlot2d<T>,
    private val chart: Chart,
    private val series: List<Serie2d>
): IPlot2d<T> by base {

    private val serieByName: Map<String, Serie2d> = series.associateBy { it.name }

    fun addPt(plotName: String, x: Float, y: Float, updateBounds: Boolean = true) {
        addPt(serieByName[plotName]!!, x, y, updateBounds)
    }

    fun addPt(plotName: String, x: Double, y: Double, updateBounds: Boolean = true) {
        addPt(serieByName[plotName]!!, x, y, updateBounds)
    }

    fun addPt(plotIndex: Int, x: Float, y: Float, updateBounds: Boolean = true) {
        addPt(series[plotIndex], x, y, updateBounds)
    }

    fun addPt(plotIndex: Int, x: Double, y: Double, updateBounds: Boolean = true) {
        addPt(series[plotIndex], x, y, updateBounds)
    }

    private fun addPt(serie2d: Serie2d, x: Float, y: Float, updateBounds: Boolean = true) {
        serie2d.add(x, y)
        if (updateBounds) {
            chart.view.updateBounds()
        }
        chart.render()
    }

    private fun addPt(serie2d: Serie2d, x: Double, y: Double, updateBounds: Boolean = true) {
        serie2d.add(x, y)
        if (updateBounds) {
            chart.view.updateBounds()
        }
        chart.render()
    }
}