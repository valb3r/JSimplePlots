package com.valb3r.jsimpleplots.plots

import com.valb3r.jsimpleplots.plots.p2d.DistributionHistogram
import com.valb3r.jsimpleplots.plots.p2d.Fft
import com.valb3r.jsimpleplots.plots.p2d.Linear
import com.valb3r.jsimpleplots.plots.p2d.Multiple2d
import com.valb3r.jsimpleplots.plots.p2d.XY
import com.valb3r.jsimpleplots.plots.p3d.Heatmap
import com.valb3r.jsimpleplots.plots.p3d.Surface
import com.valb3r.jsimpleplots.plots.p3d.WaterfallFft
import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.Chart
import org.jzy3d.chart.controllers.mouse.picking.AWTMousePickingPan2dController
import org.jzy3d.chart.controllers.mouse.picking.IMousePickingController
import org.jzy3d.chart.factories.SwingChartFactory
import org.jzy3d.chart.factories.SwingPainterFactory
import org.jzy3d.colors.Color
import kotlin.math.*

object SimplePlots {

    fun distributionHistogram(): DistributionHistogram {
        return DistributionHistogram()
    }

    fun linear(): Linear {
        return Linear()
    }

    fun xy(): XY {
        return XY()
    }

    fun heatmap(): Heatmap {
        return Heatmap()
    }

    fun surface(): Surface {
        return Surface()
    }

    fun fft(): Fft {
        return Fft()
    }

    fun waterfallFft(): WaterfallFft {
        return WaterfallFft()
    }

    fun multiple(): Multiple2d {
        return Multiple2d()
    }
}

fun FloatArray.log10(): FloatArray {
    return this.map {
        val value = log10(it)
        return@map if (value.isFinite()) value else 0.0f
    }.toFloatArray()
}