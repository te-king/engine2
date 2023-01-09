package graphics

import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import org.lwjgl.opengl.GL41C.*


interface BufferState {
    val id: Int
}


context(OpenGLScope)
@Composable
fun rememberBufferState(): BufferState {

    val bufferState = remember(Unit) {
        runBlocking(context) {
            object : BufferState {
                override val id = glGenBuffers().also(::println)
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

    return bufferState

}