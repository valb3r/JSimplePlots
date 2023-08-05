package com.valb3r.jsimpleplots.plots.p3d

import org.jzy3d.chart.Chart
import org.jzy3d.chart.factories.AWTChartFactory
import org.jzy3d.colors.ColorMapper
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.builder.SurfaceBuilder
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality

/**
 * Pseudo-2d plot - heatmap of 3d function.
 */
class Heatmap {

    private lateinit var x: FloatArray
    private lateinit var y: FloatArray
    private lateinit var z: FloatArray
    private var wireframe = false

    /**
     * X variable.
     */
    fun x(x: FloatArray): Heatmap {
        this.x = x
        return this
    }

    /**
     * Y variable.
     */
    fun y(y: FloatArray): Heatmap {
        this.y = y
        return this
    }

    /**
     * Z variable.
     */
    fun z(z: FloatArray): Heatmap {
        this.z = z
        return this
    }

    /**
     * X variable.
     */
    fun x(x: DoubleArray): Heatmap {
        this.x = x.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Y variable.
     */
    fun y(y: DoubleArray): Heatmap {
        this.y = y.map { it.toFloat() }.toFloatArray()
        return this
    }

    /**
     * Z variable.
     */
    fun z(z: DoubleArray): Heatmap {
        this.z = z.map { it.toFloat() }.toFloatArray()
        return this
    }


    /**
     * X-Y-Z tuple input in one array
     * Format is [x0,y0,z0, x1,y1,z1 ...]
     */
    fun xyz(xyz: FloatArray): Heatmap {
        val size = xyz.size / 3
        this.x = FloatArray(size)
        this.y = FloatArray(size)
        this.z = FloatArray(size)

        var pos = 0
        xyz.forEachIndexed {ind, value ->
            when (ind % 3) {
                0 -> this.x[pos] = value
                1 -> this.y[pos] = value
                else -> {this.z[pos] = value; pos++}
            }
        }
        return this
    }

    /**
     * Show wireframe.
     */
    fun wireframe(wireframe: Boolean): Heatmap {
        this.wireframe = wireframe
        return this
    }

    /**
     * Open plot in new Swing window.
     */
    fun plot(): Heatmap {
        // TODO: Assertion/truncation so X.size == Y.size == Z.size
        val surface: Shape = SurfaceBuilder().delaunay(
            x.mapIndexed { ind, xp -> Coord3d(xp, y[ind], z[ind]) }
        )
        surface.isWireframeDisplayed = wireframe
        surface.colorMapper = ColorMapper(
            ColorMapRainbow(),
            z.min().toDouble(),
            z.max().toDouble()
        )

        val chart: Chart = AWTChartFactory().newChart(Quality.Advanced())
        chart.add(surface)
        chart.view2d()
        chart.open()
        chart.addMouse()

        return this
    }
}