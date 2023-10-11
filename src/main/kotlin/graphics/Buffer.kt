package graphics

import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import math.*
import org.lwjgl.opengl.GL41C.*
import kotlin.reflect.KClass


interface BufferState<T : Any> {
    val id: Int
    val type: KClass<T>
}


context(OpenGLScope)
@Composable
fun Buffer(
    data: Float3Array
): BufferState<Float3Array> {

    val bufferState = remember(Unit) {
        runBlocking(context) {
            object : BufferState<Float3Array> {
                override val id = glGenBuffers()
                override val type = Float3Array::class
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runBlocking(context) {
                glDeleteBuffers(bufferState.id)
            }
        }
    }


    remember(data) {
        runBlocking(context) {
            glBindBuffer(GL_ARRAY_BUFFER, bufferState.id)
            glBufferData(GL_ARRAY_BUFFER, data.array, GL_STATIC_DRAW)
        }
    }


    return bufferState

}

// for int array
context(OpenGLScope)
@Composable
fun Buffer(
    data: IntArray
): BufferState<IntArray> {

    val bufferState = remember(Unit) {
        runBlocking(context) {
            object : BufferState<IntArray> {
                override val id = glGenBuffers()
                override val type = IntArray::class
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runBlocking(context) {
                glDeleteBuffers(bufferState.id)
            }
        }
    }


    // update buffer state when data changes
    remember(data) {
        runBlocking(context) {
            glBindBuffer(GL_ARRAY_BUFFER, bufferState.id)
            glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW)
        }
    }


    return bufferState

}