package math


data class Int3(val x: Int, val y: Int, val z: Int) {

    companion object {
        val zero = Int3(0, 0, 0)
        val one = Int3(1, 1, 1)

        val up = Int3(0, 1, 0)
        val down = -up
        val left = Int3(-1, 0, 0)
        val right = -left
        val forwards = Int3(0, 0, -1)
        val backwards = -forwards
    }


    constructor() : this(0, 0, 0)


    operator fun plus(other: Int3) = Int3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Int3) = Int3(x - other.x, y - other.y, z - other.z)
    operator fun unaryMinus() = Int3(-x, -y, -z)

}

