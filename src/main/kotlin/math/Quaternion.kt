package math

import kotlin.jvm.JvmInline
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@JvmInline
value class Quaternion private constructor(val vector: Float4) {

    companion object {
        val identity = Quaternion(0f, 0f, 0f, 1f)

        fun fromAxisAngle(axis: Float3, angle: Float) =
            if (axis.lengthSquared == 0.0f)
                identity
            else
                Quaternion(axis.normalized * sin(angle * 0.5f), cos(angle * 0.5f))
    }


    constructor(x: Float, y: Float, z: Float, w: Float) : this(Float4(x, y, z, w))
    constructor(v: Float3, w: Float) : this(v.x, v.y, v.z, w)
    constructor(v0: Float2, v1: Float2) : this(v0.x, v0.y, v1.x, v1.y)


    val x get() = vector.x
    val y get() = vector.y
    val z get() = vector.z
    val w get() = vector.w

    val lengthSquared get() = vector.lengthSquared
    val length get() = vector.length
    val normalized get() = Quaternion(vector.normalized)

    val axisAngle: Float4
        get() {
            val q = if (this.w <= 1.0f) this else normalized
            val w = 2.0f * acos(q.w)
            val den = sqrt(1.0f - q.w * q.w)
            return if (den > 0.0001f) Float4(Float3(q.x, q.y, q.z) / den, w) else Float4(0f, 1f, 0f, w)
        }


    operator fun plus(other: Quaternion) = Quaternion(vector + other.vector)
    operator fun minus(other: Quaternion) = Quaternion(vector - other.vector)

//    operator fun times(other: Float3): Float3 {
//        val tQvec = Float3(x, y, z)
//        var tUv = cross(tQvec, other)
//        var tUuv = cross(tQvec, tUv)
//        tUv *= 2.0f * w
//        tUuv *= 2.0f
//        return other + tUv + tUuv
//    }

    operator fun times(other: Quaternion): Quaternion {
        return Quaternion(
            x * other.w + y * other.z - z * other.y + w * other.x,
            -x * other.z + y * other.w + z * other.x + w * other.y,
            x * other.y - y * other.x + z * other.w + w * other.z,
            -x * other.x - y * other.y - z * other.z + w * other.w
        )
    }


    override fun toString() = "Quaternion(x=$x, y=$y, z=$z, w=$w)"

}