package com.valb3r.jsimpleplots.plots.p3d

import com.valb3r.jsimpleplots.plots.p2d.InternalPlot2d
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.factories.AWTChartFactory
import org.jzy3d.chart.factories.ChartFactory
import org.jzy3d.chart.factories.EmulGLChartFactory
import org.jzy3d.chart.factories.NativePainterFactory

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

internal fun chartFactory3d(): ChartFactory {
    return try {
        NativePainterFactory.detectGLProfile()
        AWTChartFactory()
    } catch (ex: Exception) {
        println("No OpenGL support found, fallback to software")
        EmulGLChartFactory()
    }
}