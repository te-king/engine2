package math

import kotlin.jvm.JvmInline


@JvmInline
value class Int2Array(val array: IntArray) : Iterable<Int2> {

    constructor(size: Int) : this(IntArray(size * 2))


    val size get() = array.size / 2


    operator fun get(index: Int): Int2 {
        return Int2(
            array[index * 2],
            array[index * 2 + 1],
        )
    }

    operator fun set(index: Int, value: Int2) {
        array[index * 2] = value.x
        array[index * 2 + 1] = value.y
    }


    override operator fun iterator() = iterator {

        for (i in 0 until size)
            yield(this@Int2Array[i])

    }

}


fun int2ArrayOf(vararg elements: Int2): Int2Array {
    val result = Int2Array(elements.size)
    elements.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Collection<Int2>.toInt2Array(): Int2Array {
    val result = Int2Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Array<out Int2>.toInt2Array(): Int2Array {
    val result = Int2Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}