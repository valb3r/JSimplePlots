[![](https://jitpack.io/v/valb3r/JSimplePlots.svg)](https://jitpack.io/#valb3r/JSimplePlots)

# What is this

This is simple plot helper library that uses Jzy3D for all heavy lifting. The idea is to implement Fluent-API
that describes available actions and to be short and concise.

# Usage

## 2D plots

### Line (X-Y)
<!-- @embed-example-start:example-2d-xy:kotlin -->
<!-- @embed-example-end -->

## 3D plots

### Heatmap
```kotlin
SimplePlots.heatmap()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f))
            .z(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f))
            .plot()
```

### Surface 
```kotlin
SimplePlots.surface()
            .x(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f))
            .y(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f))
            .z(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f))
            .plot()
```