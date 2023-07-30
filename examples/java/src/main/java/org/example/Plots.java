package org.example;

import com.valb3r.jsimpleplots.plots.SimplePlots;

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