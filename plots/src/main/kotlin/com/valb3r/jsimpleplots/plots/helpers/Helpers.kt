package com.valb3r.jsimpleplots.plots.helpers

fun FloatArray.log10(): FloatArray {
    return this.map {
        val value = kotlin.math.log10(it)
        return@map if (value.isFinite()) value else 0.0f
    }.toFloatArray()
}

fun DoubleArray.log10(): DoubleArray {
    return this.map {
        val value = kotlin.math.log10(it)
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

fun FloatArray.diffFwdFirstOrder(dt: Float = 1.0f): FloatArray {
    val result = FloatArray(this.size)
    (0..< this.size - 1).forEach {
        result[it] = (this[it + 1] - this[it]) / dt
    }
    return result
}

fun DoubleArray.diffFwdFirstOrder(dt: Double = 1.0): DoubleArray {
    val result = DoubleArray(this.size)
    (0..< this.size - 1).forEach {
        result[it] = (this[it + 1] - this[it]) / dt
    }
    return result
}