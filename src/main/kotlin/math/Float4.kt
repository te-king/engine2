package math

import kotlin.math.sqrt


data class Float4(val x: Float, val y: Float, val z: Float, val w: Float) {

    companion object {
        val zero = Float4(0f, 0f, 0f, 0f)
        val one = Float4(1f, 1f, 1f, 1f)
    }


    constructor(v0: Float2, v1: Float2) : this(v0.x, v0.y, v1.x, v1.y)
    constructor(v: Float3, w: Float) : this(v.x, v.y, v.z, w)
    constructor() : this(0f, 0f, 0f, 0f)


    val lengthSquared get() = x * x + y * y + z * z + w * w
    val length get() = sqrt(lengthSquared)
    val normalized: Float4 get() = this / length
    val xyz get() = Float3(x, y, z)
    val yzw get() = Float3(y, z, w)


    infix fun dot(v1: Float4) = x * v1.x + y * v1.y + z * v1.z + w * v1.w


    operator fun rangeTo(other: Float4) = (this - other).length
    operator fun plus(other: Float4) = Float4(x + other.x, y + other.y, z + other.z, w + other.w)
    operator fun minus(other: Float4) = Float4(x - other.x, y - other.y, z - other.z, w - other.w)
    operator fun times(scalar: Float) = Float4(x * scalar, y * scalar, z * scalar, w * scalar)
    operator fun div(scalar: Float) = Float4(x / scalar, y / scalar, z / scalar, w / scalar)
    operator fun unaryMinus() = Float4(-x, -y, -z, -w)

}