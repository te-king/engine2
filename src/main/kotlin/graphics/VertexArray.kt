package graphics

import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import math.*
import org.lwjgl.opengl.GL41C.*


interface VertexArrayState {
    val id: Int
}


context(OpenGLScope)
@Composable
fun VertexArray(
    vararg buffers: Pair<Int, BufferState<*>>,
    indexBuffer: BufferState<*>? = null
): VertexArrayState {

    val vertexArrayState = remember(Unit) {
        runBlocking(context) {
            object : VertexArrayState {
                override val id = glGenVertexArrays()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runBlocking(context) {
                glDeleteVertexArrays(vertexArrayState.id)
            }
        }
    }


    // update vertex array state when buffers change
    remember(buffers) {
        runBlocking(context) {
            glBindVertexArray(vertexArrayState.id)

            for ((index, buffer) in buffers) {
                glEnableVertexAttribArray(index)

                glBindBuffer(GL_ARRAY_BUFFER, buffer.id)
                when (buffer.type) {
                    FloatArray::class -> glVertexAttribPointer(index, 1, GL_FLOAT, false, 0, 0)
                    Float2Array::class -> glVertexAttribPointer(index, 2, GL_FLOAT, false, 0, 0)
                    Float3Array::class -> glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0)
                    Float4Array::class -> glVertexAttribPointer(index, 4, GL_FLOAT, false, 0, 0)
                    else -> throw Exception("Unsupported buffer type: ${buffer.type}")
                }
                glBindBuffer(GL_ARRAY_BUFFER, 0)
            }

            glBindVertexArray(0)
        }
    }


    // update vertex array state when index buffer changes
    remember(indexBuffer) {
        runBlocking(context) {
            glBindVertexArray(vertexArrayState.id)
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer?.id ?: 0)
            glBindVertexArray(0)
        }
    }


    return vertexArrayState

}



context(OpenGLScope)
@Composable
inline operator fun VertexArrayState.invoke(
    block: @Composable context(VertexArrayState) () -> Unit
) {
    runBlocking(context) { glBindVertexArray(id) }
    block(this)
    runBlocking(context) { glBindVertexArray(0) }
}


context(OpenGLScope, Pipeline, VertexArrayState)
@Composable
fun Draw(mode: Int, count: Int, type: Int = GL_UNSIGNED_INT, offset: Long = 0) {
    runBlocking(context) {
        glDrawElements(mode, count, type, offset)
    }
}