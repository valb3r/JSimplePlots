// @example-start:jumpstart
@file:Repository("https://jitpack.io")
@file:Repository("https://maven.jzy3d.org/releases/")
@file:DependsOn("com.github.valb3r.JSimplePlots:plots:0.0.9")

import com.valb3r.jsimpleplots.plots.SimplePlots

SimplePlots.xy()
    .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
    .y(floatArrayOf(1.0f, 4.0f, 9.0f, 16.0f))
    .width(5)
    .plot()
// @example-end