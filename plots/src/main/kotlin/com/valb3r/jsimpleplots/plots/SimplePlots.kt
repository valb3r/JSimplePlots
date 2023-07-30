package com.valb3r.jsimpleplots.plots

import com.valb3r.jsimpleplots.plots.p2d.DistributionHistogram
import com.valb3r.jsimpleplots.plots.p2d.Fft
import com.valb3r.jsimpleplots.plots.p2d.Linear
import com.valb3r.jsimpleplots.plots.p2d.Multiple2d
import com.valb3r.jsimpleplots.plots.p2d.XY
import com.valb3r.jsimpleplots.plots.p3d.Heatmap
import com.valb3r.jsimpleplots.plots.p3d.Surface
import com.valb3r.jsimpleplots.plots.p3d.WaterfallFft
import kotlin.math.log10

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

fun DoubleArray.log10(): DoubleArray {
    return this.map {
        val value = log10(it)
        return@map if (value.isFinite()) value else 0.0
    }.toDoubleArray()
}

fun FloatArray.relativeToFirstElem(): FloatArray {
    return this.map {
        it - this[0]
    }.toFloatArray()
}

fun DoubleArray.relativeToFirstElem(): DoubleArray {
    return this.map {
        it - this[0]
    }.toDoubleArray()
}