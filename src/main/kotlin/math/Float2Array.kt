package math

import kotlin.jvm.JvmInline


@JvmInline
value class Float2Array private constructor(val array: FloatArray) : Iterable<Float2> {

    constructor(size: Int) : this(FloatArray(size * 2))


    val size get() = array.size / 2


    operator fun get(index: Int): Float2 {
        return Float2(
            array[index * 2],
            array[index * 2 + 1]
        )
    }

    operator fun set(index: Int, value: Float2) {
        array[index * 2] = value.x
        array[index * 2 + 1] = value.y
    }


    override operator fun iterator() = iterator {

        for (i in 0 until size)
            yield(this@Float2Array[i])

    }

}


fun float2ArrayOf(vararg elements: Float2): Float2Array {
    val result = Float2Array(elements.size)
    elements.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Collection<Float2>.toFloat2Array(): Float2Array {
    val result = Float2Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Array<out Float2>.toFloat2Array(): Float2Array {
    val result = Float2Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}