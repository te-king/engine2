package graphics

import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import org.lwjgl.opengl.GL41C.*


interface VertexArrayState {
    val id: Int
}

interface BoundVertexArrayScope {
    val vertexArrayState: VertexArrayState
}


context(OpenGLScope)
@Composable
fun rememberVertexArrayState(
    elementArrayBuffer: BufferState? = null,
    vertexBuffers: List<BufferState> = emptyList()
): VertexArrayState {

    val vertexArrayState = remember(Unit) {
        runBlocking(context) {
            object : VertexArrayState {
                override val id = glGenVertexArrays().also(::println)
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


    // change the element array buffer of the vao when it changes
    remember(elementArrayBuffer) {
        runBlocking(context) {
            glBindVertexArray(vertexArrayState.id)
            if (elementArrayBuffer != null)
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArrayBuffer.id)
            glBindVertexArray(0)
        }
    }


    // disable the vertex buffers that are no longer used
    // change the vertex buffers of the vao when they change and enable them
    DisposableEffect(vertexBuffers) {
        runBlocking(context) {
            glBindVertexArray(vertexArrayState.id)
            for (i in vertexBuffers.indices) {
                glBindBuffer(GL_ARRAY_BUFFER, vertexBuffers[i].id)
                glEnableVertexAttribArray(i)
                glVertexAttribPointer(i, 3, GL_FLOAT, false, 0, 0)
                println("vertex buffer $i enabled with id ${vertexBuffers[i].id}")
            }
            glBindVertexArray(0)
        }

        onDispose {
            runBlocking(context) {
                glBindVertexArray(vertexArrayState.id)
                for (i in vertexBuffers.indices) {
                    glDisableVertexAttribArray(i)
                    println("vertex buffer $i disabled")
                }
                glBindVertexArray(0)
            }
        }
    }



    return vertexArrayState

}


context(OpenGLScope)
@Composable
inline fun VertexArray(
    vertexArrayState: VertexArrayState = rememberVertexArrayState(),
    content: @Composable context(BoundVertexArrayScope) () -> Unit
) {

    // only recreate scope object when vertex array state changes
    val boundVertexArrayScope = remember(vertexArrayState) {
        object : BoundVertexArrayScope {
            override val vertexArrayState = vertexArrayState
        }
    }

    // bind vertex array
    runBlocking(context) { glBindVertexArray(vertexArrayState.id) }
    content(boundVertexArrayScope)
    runBlocking(context) { glBindVertexArray(0) }

}