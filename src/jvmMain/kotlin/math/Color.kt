package math

import kotlin.jvm.JvmInline
import kotlin.random.Random


@JvmInline
value class Color(val vector: Float4) {

    constructor(r: Float, g: Float, b: Float, a: Float) : this(Float4(r, g, b, a))


    inline val r get() = vector.x
    inline val g get() = vector.y
    inline val b get() = vector.z
    inline val a get() = vector.w

    inline val rgba get() = vector
    inline val argb get() = Float4(a, r, g, b)
    inline val rgb get() = Float3(r, g, b)


    companion object {

        val red = Color(1f, 0f, 0f, 1f)
        val green = Color(0f, 1f, 0f, 1f)
        val blue = Color(0f, 0f, 1f, 1f)

        val black = Color(0f, 0f, 0f, 1f)
        val white = Color(1f, 1f, 1f, 1f)

        val transparent = Color(0f, 0f, 0f, 0f)

        fun random() = Color(
            Random.nextFloat() % 1f,
            Random.nextFloat() % 1f,
            Random.nextFloat() % 1f,
            1f
        )

    }

}