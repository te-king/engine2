package math


data class Int4(val x: Int, val y: Int, val z: Int, val w: Int) {

    companion object {
        val zero = Int4(0, 0, 0, 0)
        val one = Int4(1, 1, 1, 1)
    }


    constructor() : this(0, 0, 0, 0)


    operator fun plus(other: Int4) = Int4(x + other.x, y + other.y, z + other.z, w + other.w)
    operator fun minus(other: Int4) = Int4(x - other.x, y - other.y, z - other.z, w - other.w)
    operator fun unaryMinus() = Int4(-x, -y, -z, -w)

}