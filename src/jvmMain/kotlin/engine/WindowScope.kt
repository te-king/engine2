package engine

import androidx.compose.runtime.*
import graphics.*
import kotlinx.coroutines.*
import math.Color
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL41C.*
import java.util.concurrent.Executors


interface WindowScope {
    val window: Long
}


context(GlfwScope)
@Composable
fun Window(
    clearColor: Color,
    title: String = "New Window",
    content: @Composable context(WindowScope, OpenGLScope) () -> Unit
) {

    // create window scope on first composition
    val windowScope = remember(Unit) {
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)

        val window = glfwCreateWindow(640, 480, title, 0, 0)

        object : WindowScope {
            override val window = window
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            glfwDestroyWindow(windowScope.window)
        }
    }


    // create opengl scope on first composition
    val openGlScope = remember(Unit) {
        val context = Executors
            .newSingleThreadExecutor()
            .asCoroutineDispatcher()

        val capabilities = runBlocking(context) {
            glfwMakeContextCurrent(windowScope.window)
            GL.createCapabilities()
        }

        object : OpenGLScope {
            override val capabilities = capabilities
            override val context = context
        }
    }


    // update the title and clear color when they change
    remember(title) {
        glfwSetWindowTitle(windowScope.window, title)
    }

    remember(clearColor) {
        runBlocking(openGlScope.context) {
            glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a)
        }
    }


    // clear back buffer, run the content, and swap buffers
    runBlocking(openGlScope.context) { glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) }
    content(windowScope, openGlScope)
    runBlocking(openGlScope.context) { glfwSwapBuffers(windowScope.window) }

}