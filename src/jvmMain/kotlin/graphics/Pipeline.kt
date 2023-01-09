package graphics


import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import org.lwjgl.opengl.GL41C.*


interface ShaderState {
    val shaderId: Int
}


context(OpenGLScope)
@Composable
fun Shader(
    type: Int,
    source: String,
): ShaderState {

    val shaderState = remember(source) {
        runBlocking(context) {
            val result = object : ShaderState {
                override val shaderId = glCreateShaderProgramv(type, source)
            }

            glGetProgramInfoLog(result.shaderId).let(::println)

            result
        }
    }

    DisposableEffect(source) {
        onDispose {
            runBlocking(context) {
                glDeleteProgram(shaderState.shaderId)
            }
        }
    }


    return shaderState

}


interface PipelineState {
    val pipelineId: Int
}


context(OpenGLScope)
@Composable
fun Pipeline(
    vertexShader: ShaderState,
    fragmentShader: ShaderState
): PipelineState {

    val pipelineState = remember(Unit) {
        runBlocking(context) {
            val result = object : PipelineState {
                override val pipelineId = glGenProgramPipelines()
            }

            glGetProgramPipelineInfoLog(result.pipelineId).let(::println)

            result
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runBlocking(context) {
                glDeleteProgramPipelines(pipelineState.pipelineId)
            }
        }
    }


    // update pipeline state when shaders change
    remember(vertexShader, fragmentShader) {
        runBlocking(context) {
            glUseProgramStages(pipelineState.pipelineId, GL_VERTEX_SHADER_BIT, vertexShader.shaderId)
            glUseProgramStages(pipelineState.pipelineId, GL_FRAGMENT_SHADER_BIT, fragmentShader.shaderId)
        }
    }


    return pipelineState

}


context(OpenGLScope)
@Composable
inline operator fun PipelineState.invoke(
    block: @Composable context(PipelineState) () -> Unit
) {
    runBlocking(context) { glBindProgramPipeline(pipelineId) }
    block(this)
    runBlocking(context) { glBindProgramPipeline(0) }
}