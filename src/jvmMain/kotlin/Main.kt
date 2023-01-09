import androidx.compose.runtime.*
import engine.*
import graphics.*
import kotlinx.coroutines.delay
import math.Color
import org.lwjgl.opengl.GL41C.*
import utilities.compose

context(OpenGLScope)
fun triangle() {

}

fun main() = compose {

    var clearColor by remember { mutableStateOf(Color.red) }
    var vertexCount by remember { mutableStateOf(3) }


    Glfw {

        Window(clearColor, title = "Hello World") {

            val vao = rememberVertexArrayState(
                elementArrayBuffer = rememberBufferState(),
                vertexBuffers = List(vertexCount) { rememberBufferState() }
            )

            VertexArray(vao) {

            }

        }

    }


    // change clearColor every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            clearColor = Color.random()
        }
    }

    // change vertexCount after 5 seconds
    LaunchedEffect(Unit) {
        delay(5000)
        vertexCount = 4
    }

}