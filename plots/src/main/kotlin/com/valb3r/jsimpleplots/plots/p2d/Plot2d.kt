package com.valb3r.jsimpleplots.plots.p2d

import com.jogamp.opengl.util.texture.TextureData
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.chart.controllers.mouse.picking.AWTMousePickingPan2dController
import org.jzy3d.chart.controllers.mouse.picking.IMousePickingController
import org.jzy3d.chart.factories.AWTChartFactory
import org.jzy3d.chart.factories.AWTPainterFactory
import org.jzy3d.chart.factories.ChartFactory
import org.jzy3d.chart.factories.EmulGLChartFactory
import org.jzy3d.chart.factories.EmulGLPainterFactory
import org.jzy3d.chart.factories.NativePainterFactory
import org.jzy3d.chart.factories.SwingPainterFactory
import org.jzy3d.colors.Color
import org.jzy3d.colors.Color.COLORS
import org.jzy3d.io.AWTImageExporter
import org.jzy3d.plot3d.rendering.view.AWTRenderer3d
import org.jzy3d.plot3d.rendering.view.Renderer3d
import org.jzy3d.plot3d.rendering.view.View
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import javax.imageio.ImageIO

abstract class Plot2d<T: Plot2d<T>>(protected var name: String) {

    protected var color = COLORS[0]
    protected var width = 1

    protected var fontFace = "Helvectica"
    protected var fontSize = 12
    protected var axisFontSize = 30

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
     * Set plot color.
     */
    fun color(color: Color): T {
        this.color = color
        return this as T
    }

    /**
     * Set plot width (boldness).
     */
    fun width(width: Int): T {
        this.width = width
        return this as T
    }

    /**
     * Takes screenshot of a chart, allowing to i.e. save it to file.
     */
    fun screenshot(): ScreenshotPlot2d<T> {
        return ScreenshotPlot2d(this)
    }

    internal abstract fun internalRepresentation(offscreen: Offscreen2d? = null): InternalPlot2d
}

class ScreenshotPlot2d<T: Plot2d<T>>(private val plot: Plot2d<T>) {

    /**
     * Save chart screenshot to file.
     * @param file File to save to
     * @param format Image format (png, jpeg, ...) according to ImageIO
     */
    fun saveToFile(file: File, format: String = "png", size: Rectangle = Rectangle(800, 600)): ScreenshotPlot2d<T> {
        val offscreen = Offscreen2d(rectangle = size)
        val chart = plot.internalRepresentation(offscreen = offscreen).chart
        chart.view2d()
        when (val screenshot = chart.canvas.screenshot()) {
            is TextureData -> ImageIO.write(offscreen.lastRenderedImage.get(), format, file)
            is BufferedImage -> ImageIO.write(screenshot, format, file)
        }
        return this
    }

    /**
     * Save chart screenshot to buffered image.
     * @param toImage Container for the image, will receive the result
     */
    fun toBufferedImage(toImage: AtomicReference<BufferedImage>, size: Rectangle = Rectangle(800, 600)): ScreenshotPlot2d<T> {
        val offscreen = Offscreen2d(rectangle = size)
        val chart = plot.internalRepresentation(offscreen = offscreen).chart
        chart.view2d()
        when (val screenshot = chart.canvas.screenshot()) {
            is TextureData -> toImage.set(offscreen.lastRenderedImage.get())
            is BufferedImage -> toImage.set(screenshot)
        }
        return this
    }

    /**
     * Back to plot.
     */
    fun plot(): Plot2d<T> {
        return plot
    }
}

internal fun chartFactory2d(offscreen: Offscreen2d? = null): ChartFactory {
    val factory = try {
        NativePainterFactory.detectGLProfile()
        val f = AWTChartFactory()
        f.painterFactory = object : SwingPainterFactory() { // AWTPainterFactory does not detect resize properly
            override fun newMousePickingController(chart: Chart?, clickWidth: Int): IMousePickingController {
                return AWTMousePickingPan2dController(chart, clickWidth)
            }

            override fun newRenderer3D(view: View?): Renderer3d {
                val renderer = super.newRenderer3D(view) as AWTRenderer3d
                renderer.exporter = object : AWTImageExporter {
                    override fun export(image: BufferedImage?) {
                        offscreen?.apply { this.lastRenderedImage.set(image) }
                    }

                    override fun terminate(timeout: Long, unit: TimeUnit?): Boolean {
                        return true
                    }
                }
                return renderer
            }
        }

        f
    } catch (ex: Exception) {
        println("No OpenGL support found, fallback to software")
        val f = EmulGLChartFactory()
        f.painterFactory = object : EmulGLPainterFactory() {
            override fun newMousePickingController(chart: Chart?, clickWidth: Int): IMousePickingController {
                return AWTMousePickingPan2dController(chart, clickWidth)
            }
        }

        f
    }

    offscreen?.apply {
        factory.painterFactory.setOffscreen(
            org.jzy3d.maths.Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
        )
    }
    return factory
}

internal class Offscreen2d(
    val rectangle: Rectangle = Rectangle(800, 600),
    val lastRenderedImage: AtomicReference<BufferedImage> = AtomicReference<BufferedImage>()
)

internal fun enableMouse(chart: AWTChart) {
    chart.addMouse()
    chart.addMousePickingController(5)
}