package graphics


import androidx.compose.runtime.*
import kotlinx.coroutines.runBlocking
import org.lwjgl.opengl.GL41C.*


@JvmInline
value class Pipeline(val id: Int)


context(OpenGLScope)
@Composable
fun Pipeline(
    vertexShader: Shader,
    fragmentShader: Shader,
    block: @Composable context(Pipeline) () -> Unit
) {

    val pipeline = remember(Unit) {
        runBlocking(context) { glGenProgramPipelines().let(::Pipeline) }
    }

    DisposableEffect(Unit) {
        onDispose {
            runBlocking(context) { glDeleteProgramPipelines(pipeline.id) }
        }
    }


    // update pipeline state when shaders change
    remember(vertexShader, fragmentShader) {
        runBlocking(context) {
            glUseProgramStages(pipeline.id, GL_VERTEX_SHADER_BIT, vertexShader.id)
            glUseProgramStages(pipeline.id, GL_FRAGMENT_SHADER_BIT, fragmentShader.id)
        }
    }


    // bind pipeline and execute block
    runBlocking(context) { glBindProgramPipeline(pipeline.id) }
    block(pipeline)
    runBlocking(context) { glBindProgramPipeline(0) }

}