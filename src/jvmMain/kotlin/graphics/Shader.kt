package graphics

import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import org.lwjgl.opengl.GL41C.*

@JvmInline
value class Shader(val id: Int)


context(OpenGLScope)
@Composable
fun compiledShaderOf(
    type: Int,
    source: String,
): Shader {

    val shader = remember(source) {
        runBlocking(context) { glCreateShaderProgramv(type, source).let(::Shader) }
    }

    DisposableEffect(source) {
        onDispose {
            runBlocking(context) { glDeleteProgram(shader.id) }
        }
    }


    return shader

}