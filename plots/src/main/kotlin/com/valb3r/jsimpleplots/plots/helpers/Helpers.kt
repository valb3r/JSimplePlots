package com.valb3r.jsimpleplots.plots.helpers

/**
 * Takes logarithm with base 10 of the array.
 */
fun FloatArray.log10(): FloatArray {
    return this.map {
        val value = kotlin.math.log10(it)
        return@map if (value.isFinite()) value else 0.0f
    }.toFloatArray()
}

/**
 * Takes logarithm with base 10 of the array.
 */
fun DoubleArray.log10(): DoubleArray {
    return this.map {
        val value = kotlin.math.log10(it)
        return@map if (value.isFinite()) value else 0.0
    }.toDoubleArray()
}

/**
 * Subtracts 1st element from all array elements.
 */
fun FloatArray.relativeToFirstElem(): FloatArray {
    return this.map {
        it - this[0]
    }.toFloatArray()
}

/**
 * Subtracts 1st element from all array elements.
 */
fun DoubleArray.relativeToFirstElem(): DoubleArray {
    return this.map {
        it - this[0]
    }.toDoubleArray()
}

/**
 * @param dt - timestep
 * First order forward differentiation of array.
 */
fun FloatArray.diffFwdFirstOrder(dt: Float = 1.0f): FloatArray {
    val result = FloatArray(this.size)
    (0..< this.size - 1).forEach {
        result[it] = (this[it + 1] - this[it]) / dt
    }
    return result
}

/**
 * @param dt - timestep
 * First order forward differentiation of array.
 */
fun DoubleArray.diffFwdFirstOrder(dt: Double = 1.0): DoubleArray {
    val result = DoubleArray(this.size)
    (0..< this.size - 1).forEach {
        result[it] = (this[it + 1] - this[it]) / dt
    }
    return result
}