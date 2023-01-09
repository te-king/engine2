package graphics

import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import org.lwjgl.opengl.GL41C.*


interface TextureState {
    val id: Int
}


context (OpenGLScope)
@Composable
fun rememberTextureState(): TextureState {

    val textureState = remember(Unit) {
        runBlocking(context) {
            object : TextureState {
                override val id = glGenTextures()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runBlocking(context) {
                glDeleteTextures(textureState.id)
            }
        }
    }

    return textureState

}