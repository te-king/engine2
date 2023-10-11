package utilities

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.system.exitProcess


private object UnitApplier : Applier<Unit> {
    override val current = Unit
    override fun clear() = Unit
    override fun move(from: Int, to: Int, count: Int) = Unit
    override fun remove(index: Int, count: Int) = Unit
    override fun up() = Unit
    override fun insertTopDown(index: Int, instance: Unit) = Unit
    override fun insertBottomUp(index: Int, instance: Unit) = Unit
    override fun down(node: Unit) = Unit
}

private object YieldFrameClock : MonotonicFrameClock {
    override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R) =
        onFrame(System.nanoTime())
}


fun interface ComposeContext {
    fun closeComposition()
}


suspend fun awaitCompose(content: @Composable ComposeContext.() -> Unit) {
    withContext(YieldFrameClock) {

        // Set up a channel to transmit recompose notifications
        val channel = Channel<Any>(Channel.CONFLATED)
        Snapshot.registerGlobalWriteObserver(channel::trySend)

        // create and begin composition
        val recomposer = Recomposer(coroutineContext)
        val composition = Composition(UnitApplier, recomposer)
        val context = ComposeContext(recomposer::cancel)

        try {
            launch { channel.consumeEach { Snapshot.sendApplyNotifications() } }
            launch { recomposer.runRecomposeAndApplyChanges() }
            composition.setContent { context.content() }
            recomposer.close()
            recomposer.join()
        } finally {
            composition.dispose()
        }

    }
}


fun compose(
    exitOnNoOp: Boolean = true,
    content: @Composable ComposeContext.() -> Unit
) = runBlocking {
    awaitCompose {
        content()
    }

    if (exitOnNoOp) exitProcess(0)
}

