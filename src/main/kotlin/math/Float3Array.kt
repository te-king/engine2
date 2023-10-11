package math

import kotlin.jvm.JvmInline


@JvmInline
value class Float3Array private constructor(val array: FloatArray) : Iterable<Float3> {

    constructor(size: Int) : this(FloatArray(size * 3))


    val size get() = array.size / 3


    operator fun get(index: Int): Float3 {
        return Float3(
            array[index * 3],
            array[index * 3 + 1],
            array[index * 3 + 2]
        )
    }

    operator fun set(index: Int, value: Float3) {
        array[index * 3] = value.x
        array[index * 3 + 1] = value.y
        array[index * 3 + 2] = value.z
    }


    override operator fun iterator() = iterator {

        for (i in 0 until size)
            yield(this@Float3Array[i])

    }

}


fun float3ArrayOf(vararg elements: Float3): Float3Array {
    val result = Float3Array(elements.size)
    elements.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Collection<Float3>.toFloat3Array(): Float3Array {
    val result = Float3Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Array<out Float3>.toFloat3Array(): Float3Array {
    val result = Float3Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}