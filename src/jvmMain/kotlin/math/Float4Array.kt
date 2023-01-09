package math

import kotlin.jvm.JvmInline


@JvmInline
value class Float4Array private constructor(val array: FloatArray) : Iterable<Float4> {

    constructor(size: Int) : this(FloatArray(size * 4))


    val size get() = array.size / 4


    operator fun get(index: Int): Float4 {
        return Float4(
            array[index * 4],
            array[index * 4 + 1],
            array[index * 4 + 2],
            array[index * 4 + 3]
        )
    }

    operator fun set(index: Int, value: Float4) {
        array[index * 4] = value.x
        array[index * 4 + 1] = value.y
        array[index * 4 + 2] = value.z
        array[index * 4 + 3] = value.w
    }


    override operator fun iterator() = iterator {

        for (i in 0 until size)
            yield(this@Float4Array[i])

    }

}


fun float4ArrayOf(vararg elements: Float4): Float4Array {
    val result = Float4Array(elements.size)
    elements.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Collection<Float4>.toFloat4Array(): Float4Array {
    val result = Float4Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Array<out Float4>.toFloat4Array(): Float4Array {
    val result = Float4Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}