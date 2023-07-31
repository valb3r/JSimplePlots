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