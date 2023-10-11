package graphics

import org.lwjgl.opengl.GLCapabilities
import kotlin.coroutines.CoroutineContext


interface OpenGLScope {
    val capabilities: GLCapabilities
    val context: CoroutineContext
}