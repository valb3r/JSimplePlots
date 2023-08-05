package com.valb3r.jsimpleplots.plots.p3d

import com.valb3r.jsimpleplots.plots.p2d.InternalPlot2d
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.factories.SwingChartFactory
import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS

abstract class Plot3d<T: Plot3d<T>>(protected var name: String) {

    protected var fontFace = "Helvectica"
    protected var fontSize = 12
    protected var axisFontSize = 30
    protected var wireframe = false

    /**
     * Set legend font.
     * @param fontSize Font size of the legend
     * @param axisFontSize Font size of axis labels
     * @param fontFace Font face for axis and legend
     */
    fun font(fontSize: Int? = null, axisFontSize: Int? = null, fontFace: String? = null): T  {
        fontSize?.let { this.fontSize = it }
        axisFontSize?.let { this.axisFontSize = it }
        fontFace?.let { this.fontFace = it }
        return this as T
    }

    /**
     * Set plot name.
     */
    fun named(name: String): T {
        this.name = name
        return this as T
    }

    /**
     * Show wireframe.
     */
    fun wireframe(wireframe: Boolean): T {
        this.wireframe = wireframe
        return this as T
    }

    internal abstract fun internalRepresentation(): InternalPlot2d
}

internal fun swingChartFactory3d(): SwingChartFactory {
    return SwingChartFactory()
}

internal fun enableMouse(chart: AWTChart) {
    chart.addMouse()
    chart.addMousePickingController(5)
}