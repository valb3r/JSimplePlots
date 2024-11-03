package org.example;

import com.valb3r.jsimpleplots.plots.SimplePlots;

import java.awt.*;
import java.io.File;
import java.util.stream.IntStream;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class Plots {
    public static void main(String[] args) {
        // @example-start:example-2d-xy
        SimplePlots.INSTANCE.xy()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 4.0f, 9.0f, 16.0f})
                .plot();
        // @example-end
    }
}

class DynamicPlots {
    public static void main(String[] args) {
        // @example-start:example-2d-xy-dynamic
        var aPlot = SimplePlots.INSTANCE.xy()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 4.0f, 9.0f, 16.0f})
                .plot();

        for (int i = 0; i < 10; i++) {
            aPlot.addPt(Math.random() * 10.0, Math.random() * 10.0, true);
            try { Thread.sleep(1000L);} catch (InterruptedException ignored) {}
        }
        // @example-end
    }
}

class PlotsScreenshot {
    public static void main(String[] args) {
        // @example-start:example-2d-xy-screenshot
        SimplePlots.INSTANCE.xy()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 4.0f, 9.0f, 16.0f})
                .screenshot()
                .saveToFile(new File("xy.png"), "png", new Rectangle(500, 500));
        // @example-end
    }
}

class PlotsMultiple {
    public static void main(String[] args) {
        // @example-start:example-2d-xy-multiple
        var parabola = SimplePlots.INSTANCE.xy()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 4.0f, 9.0f, 16.0f})
                .named("Parabola");
        var cubic = SimplePlots.INSTANCE.xy()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 8.0f, 27.0f, 64.0f})
                .named("Cubic");

        SimplePlots.INSTANCE.multiple()
                .add(parabola)
                .add(cubic)
                .plot();
        // @example-end
    }
}

class Plots2dFft {
    public static void main(String[] args) {
        var samplingFrequency = 800.0f;
        var data = IntStream.range(0, 500)
                .mapToDouble(it -> 0.4f * sin(2.0 * PI * 150 * it / samplingFrequency))
                .toArray();

        // @example-start:example-2d-fft
        SimplePlots.INSTANCE.fft()
                .samplingFrequency(samplingFrequency)
                .y(data)
                .plot();
        // @example-end
    }
}

class Plots3dHeatmap {
    public static void main(String[] args) {
        // @example-start:example-heatmap
        SimplePlots.INSTANCE.heatmap()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f})
                .z(new float[] {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f})
                .plot();
        // @example-end
    }
}

class Plots3dSurface {
    public static void main(String[] args) {
        // @example-start:example-3d-surface
        SimplePlots.INSTANCE.surface()
                .x(new float[] {1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f})
                .y(new float[] {1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f})
                .z(new float[] {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f})
                .plot();
        // @example-end
    }
}

class Plots3dFft {
    public static void main(String[] args) {
        var samplingFrequency = 800.0f;
        var data = IntStream.range(0, 5000)
                .mapToDouble(it -> 0.4f * sin(2.0 * PI * 150 * it / samplingFrequency))
                .toArray();
        // @example-start:example-fft-heatmap
        SimplePlots.INSTANCE.waterfallFft()
                .y(data)
                .chunkSize(100)
                .samplingFrequency(samplingFrequency)
                .plot();
        // @example-end
    }
}

class Plots3dFftScreenshot {
    public static void main(String[] args) {
        var samplingFrequency = 800.0f;
        var data = IntStream.range(0, 5000)
                .mapToDouble(it -> 0.4f * sin(2.0 * PI * 150 * it / samplingFrequency))
                .toArray();
        // @example-start:example-fft-heatmap-screenshot
        SimplePlots.INSTANCE.waterfallFft()
                .y(data)
                .chunkSize(100)
                .samplingFrequency(samplingFrequency)
                .screenshot()
                .saveToFile(new File("heatmap.png"), "png", new Rectangle(500, 500));
        // @example-end
    }
}