package com.github.freekdb.kotlin.workshop.step06.main

import com.github.freekdb.kotlin.workshop.step06.visualization.CoroutinesVisualized
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.concurrent.Executors

const val TASK_COUNT = 12
const val STEP_COUNT = 3

private val updateChannel = Channel<ExecutedStep>()

fun main() {
    val threadCount = Runtime.getRuntime().availableProcessors()

    val visualizationGui = CoroutinesVisualized(LocalDateTime.now(), threadCount)
    visualizationGui.initializeGui()

    val coroutineDispatcher = Executors.newFixedThreadPool(threadCount).asCoroutineDispatcher()

    coroutineDispatcher
            .use { context ->
                runBlocking {
                    visualizationGui.processUpdates(this, updateChannel)

                    repeat(TASK_COUNT) {
                        launch(context) {
                            runTask(taskId = it + 1)
                        }
                    }
                }
            }
}

suspend fun runTask(taskId: Int) {
    updateExecutedStep(taskId, 0, LocalDateTime.now())
    yield()

    repeat(STEP_COUNT) {
        val startDateTime = LocalDateTime.now()

        fibonacci((2000000 + it * taskId * 160000) / TASK_COUNT)

        updateExecutedStep(taskId, it + 1, startDateTime)
        yield()
    }
}

private fun fibonacci(index: Int): BigInteger {
    var number1 = BigInteger.ZERO
    var number2 = BigInteger.ONE

    repeat(index) {
        val sum = number1 + number2
        number1 = number2
        number2 = sum
    }

    return number1
}

suspend fun updateExecutedStep(taskId: Int, stepNumber: Int, startDateTime: LocalDateTime) {
    updateChannel.send(ExecutedStep(determineThreadNumber(), taskId, stepNumber, startDateTime, LocalDateTime.now()))
}

private fun determineThreadNumber(): Int {
    val threadName = Thread.currentThread().name

    return threadName.substring(threadName.lastIndexOf('-') + 1).toInt()
}


data class ExecutedStep(val threadNumber: Int, val taskId: Int, val stepNumber: Int,
                        val startTime: LocalDateTime, val endTime: LocalDateTime)
