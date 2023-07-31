package com.valb3r.jsimpleplots.plots.p2d

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.chart.controllers.mouse.picking.AWTMousePickingPan2dController
import org.jzy3d.chart.controllers.mouse.picking.IMousePickingController
import org.jzy3d.chart.factories.SwingChartFactory
import org.jzy3d.chart.factories.SwingPainterFactory
import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS

abstract class Plot2d<T: Plot2d<T>>(protected var name: String) {

    protected var color = COLORS[0]
    protected var width = 1

    protected var fontFace = "Helvectica"
    protected var fontSize = 12
    protected var axisFontSize = 30

    fun font(fontSize: Int? = null, axisFontSize: Int? = null, fontFace: String? = null): T  {
        fontSize?.let { this.fontSize = it }
        axisFontSize?.let { this.axisFontSize = it }
        fontFace?.let { this.fontFace = it }
        return this as T
    }

    fun named(name: String): T {
        this.name = name
        return this as T
    }

    fun color(color: Color): T {
        this.color = color
        return this as T
    }

    fun width(width: Int): T {
        this.width = width
        return this as T
    }

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