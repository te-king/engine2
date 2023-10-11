package engine

import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.lwjgl.glfw.GLFW.*


interface GlfwScope {
    val glfwVersion: String
}


@Composable
fun Glfw(
    content: @Composable context(GlfwScope) () -> Unit
) {

    // create glfw scope on first composition
    val glfwScope = remember(Unit) {
        glfwInit()

        object : GlfwScope {
            override val glfwVersion = glfwGetVersionString()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            glfwTerminate()
        }
    }


    // start the event look on the current thread, poll for window events every 50ms
    LaunchedEffect(Unit) {
        (0..Long.MAX_VALUE)
            .asSequence()
            .asFlow()
            .onEach { delay(50) }
            .collect { glfwPollEvents() }
    }


    // on every recomposition, call the content block
    content(glfwScope)

}