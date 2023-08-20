package com.valb3r.jsimpleplots.plots.p3d

import com.jogamp.opengl.util.texture.TextureData
import com.valb3r.jsimpleplots.plots.p2d.InternalPlot2d
import com.valb3r.jsimpleplots.plots.p2d.ScreenshotPlot2d
import org.jzy3d.chart.factories.AWTChartFactory
import org.jzy3d.chart.factories.AWTPainterFactory
import org.jzy3d.chart.factories.ChartFactory
import org.jzy3d.chart.factories.EmulGLChartFactory
import org.jzy3d.chart.factories.NativePainterFactory
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

    /**
     * Takes screenshot of a chart, allowing to i.e. save it to file.
     */
    fun screenshot(): ScreenshotPlot3d<T> {
        return ScreenshotPlot3d(this)
    }

    internal abstract fun internalRepresentation(offscreen: Offscreen3d? = null): InternalPlot2d
}

class ScreenshotPlot3d<T: Plot3d<T>>(private val plot: Plot3d<T>) {

    /**
     * Save chart screenshot to file.
     * @param file File to save to
     * @param format Image format (png, jpeg, ...) according to ImageIO
     */
    fun saveToFile(file: File, format: String = "png", size: Rectangle = Rectangle(800, 600)): ScreenshotPlot3d<T> {
        val offscreen = Offscreen3d(rectangle = size)
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
    fun toBufferedImage(toImage: AtomicReference<BufferedImage>, size: Rectangle = Rectangle(800, 600)): ScreenshotPlot3d<T> {
        val offscreen = Offscreen3d(rectangle = size)
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
    fun plot(): Plot3d<T> {
        return plot
    }
}

internal fun chartFactory3d(offscreen: Offscreen3d? = null): ChartFactory {
    val factory = try {
        NativePainterFactory.detectGLProfile()
        val f = AWTChartFactory()
        f.painterFactory = object : AWTPainterFactory() {
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
        EmulGLChartFactory()
    }

    offscreen?.apply {
        factory.painterFactory.setOffscreen(
            org.jzy3d.maths.Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
        )
    }

    return factory
}

internal class Offscreen3d(
    val rectangle: Rectangle = Rectangle(800, 600),
    val lastRenderedImage: AtomicReference<BufferedImage> = AtomicReference<BufferedImage>()
)