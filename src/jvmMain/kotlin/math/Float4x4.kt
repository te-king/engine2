package math


data class Float4x4(
    val m00: Float, val m01: Float, val m02: Float, val m03: Float,
    val m10: Float, val m11: Float, val m12: Float, val m13: Float,
    val m20: Float, val m21: Float, val m22: Float, val m23: Float,
    val m30: Float, val m31: Float, val m32: Float, val m33: Float
) : Iterable<Float> {

    companion object {
        val identity
            get() = Float4x4(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
    }


    fun column(index: Int) = when (index) {
        0 -> Float4(m00, m01, m02, m03)
        1 -> Float4(m10, m11, m12, m13)
        2 -> Float4(m20, m21, m22, m23)
        3 -> Float4(m30, m31, m32, m33)
        else -> throw IndexOutOfBoundsException("$index")
    }


    operator fun times(other: Float4x4) = Float4x4(
        other.m00 * this.m00 + other.m01 * this.m10 + other.m02 * this.m20 + other.m03 * this.m30,
        other.m00 * this.m01 + other.m01 * this.m11 + other.m02 * this.m21 + other.m03 * this.m31,
        other.m00 * this.m02 + other.m01 * this.m12 + other.m02 * this.m22 + other.m03 * this.m32,
        other.m00 * this.m03 + other.m01 * this.m13 + other.m02 * this.m23 + other.m03 * this.m33,
        other.m10 * this.m00 + other.m11 * this.m10 + other.m12 * this.m20 + other.m13 * this.m30,
        other.m10 * this.m01 + other.m11 * this.m11 + other.m12 * this.m21 + other.m13 * this.m31,
        other.m10 * this.m02 + other.m11 * this.m12 + other.m12 * this.m22 + other.m13 * this.m32,
        other.m10 * this.m03 + other.m11 * this.m13 + other.m12 * this.m23 + other.m13 * this.m33,
        other.m20 * this.m00 + other.m21 * this.m10 + other.m22 * this.m20 + other.m23 * this.m30,
        other.m20 * this.m01 + other.m21 * this.m11 + other.m22 * this.m21 + other.m23 * this.m31,
        other.m20 * this.m02 + other.m21 * this.m12 + other.m22 * this.m22 + other.m23 * this.m32,
        other.m20 * this.m03 + other.m21 * this.m13 + other.m22 * this.m23 + other.m23 * this.m33,
        other.m30 * this.m00 + other.m31 * this.m10 + other.m32 * this.m20 + other.m33 * this.m30,
        other.m30 * this.m01 + other.m31 * this.m11 + other.m32 * this.m21 + other.m33 * this.m31,
        other.m30 * this.m02 + other.m31 * this.m12 + other.m32 * this.m22 + other.m33 * this.m32,
        other.m30 * this.m03 + other.m31 * this.m13 + other.m32 * this.m23 + other.m33 * this.m33
    )

    operator fun times(other: Float4) = Float4(
        this.m00 * other.x + this.m01 * other.y + this.m02 * other.z + this.m03 * other.w,
        this.m10 * other.x + this.m11 * other.y + this.m12 * other.z + this.m13 * other.w,
        this.m20 * other.x + this.m21 * other.y + this.m22 * other.z + this.m23 * other.w,
        this.m30 * other.x + this.m31 * other.y + this.m32 * other.z + this.m33 * other.w
    )


    override fun iterator() = iterator {
        yield(m00)
        yield(m01)
        yield(m02)
        yield(m03)
        yield(m10)
        yield(m11)
        yield(m12)
        yield(m13)
        yield(m20)
        yield(m21)
        yield(m22)
        yield(m23)
        yield(m30)
        yield(m31)
        yield(m32)
        yield(m33)
    }

}