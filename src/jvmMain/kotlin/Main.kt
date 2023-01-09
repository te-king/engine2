import androidx.compose.runtime.*
import engine.*
import graphics.*
import kotlinx.coroutines.*
import math.*
import org.lwjgl.opengl.GL41C.*
import org.lwjgl.opengl.GLUtil
import utilities.compose


val vertexShader = """
    #version 400 core
    
    layout (location = 0) in vec3 position;
    
    out gl_PerVertex {
        vec4 gl_Position;     // makes gl_Position is part of interface
        float gl_PointSize;   // makes gl_PointSize is part of interface
    };                        // no more members of gl_PerVertex are used
    
    void main() {
        gl_Position = vec4(position, 1.0);
    }
""".trimIndent()


val fragmentShader = """
    #version 400 core
    
    out vec4 color;
    
    void main() {
        color = vec4(1.0, 1.0, 1.0, 1.0);
    }
""".trimIndent()


val triangleVerticies = float3ArrayOf(
    Float3(0f, 0f, 0f),
    Float3(0.5f, 0f, 0f),
    Float3(0f, 0.5f, 0f),
)

val triangleIndices = intArrayOf(
    0, 1, 2
)

fun main() = compose {

    var clearColor by remember { mutableStateOf(Color.red) }


    Glfw {

        Window(clearColor, title = "Hello World") {

            val vao = VertexArray(
                0 to Buffer(triangleVerticies),
                indexBuffer = Buffer(triangleIndices)
            )


            val pipeline = Pipeline(
                vertexShader = Shader(GL_VERTEX_SHADER, vertexShader),
                fragmentShader = Shader(GL_FRAGMENT_SHADER, fragmentShader)
            )


            pipeline {
                vao {
                    Draw(GL_TRIANGLES, 3)
                }
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

}