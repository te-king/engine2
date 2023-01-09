package math

import kotlin.jvm.JvmInline


@JvmInline
value class Int3Array(val array: IntArray) : Iterable<Int3> {

    constructor(size: Int) : this(IntArray(size * 3))


    val size get() = array.size / 3


    operator fun get(index: Int): Int3 {
        return Int3(
            array[index * 3],
            array[index * 3 + 1],
            array[index * 3 + 2]
        )
    }

    operator fun set(index: Int, value: Int3) {
        array[index * 3] = value.x
        array[index * 3 + 1] = value.y
        array[index * 3 + 2] = value.z
    }


    override operator fun iterator() = iterator {

        for (i in 0 until size)
            yield(this@Int3Array[i])

    }

}


fun int3ArrayOf(vararg elements: Int3): Int3Array {
    val result = Int3Array(elements.size)
    elements.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Collection<Int3>.toInt3Array(): Int3Array {
    val result = Int3Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}

fun Array<out Int3>.toInt3Array(): Int3Array {
    val result = Int3Array(size)
    this.forEachIndexed { i, f -> result[i] = f }
    return result
}