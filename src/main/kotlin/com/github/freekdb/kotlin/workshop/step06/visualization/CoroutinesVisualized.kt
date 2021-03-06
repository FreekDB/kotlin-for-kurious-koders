package com.github.freekdb.kotlin.workshop.step06.visualization

import com.github.freekdb.kotlin.workshop.step06.main.ExecutedStep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.swing.AbstractAction
import javax.swing.JFrame
import javax.swing.KeyStroke
import javax.swing.WindowConstants
import kotlin.system.exitProcess

private const val FRAME_TITLE_PREFIX = "Coroutines Visualized"

class CoroutinesVisualized(private val startDateTime: LocalDateTime, threadCount: Int) {
    private val frame = JFrame(FRAME_TITLE_PREFIX)
    private val stepsPanel = StepsPanel(threadCount, startDateTime)

    fun initializeGui() {
        frame.setBounds(200, 100, 1550, 832)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        frame.contentPane.add(stepsPanel)

        // Exit the program whenever the user presses the Escape key.
        val exitActionKey = "exit on escape"
        stepsPanel.inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), exitActionKey)
        stepsPanel.actionMap.put(exitActionKey, object : AbstractAction(exitActionKey) {
            override fun actionPerformed(keyEvent: ActionEvent?) {
                exitProcess(0)
            }
        })

        frame.isVisible = true
    }

    suspend fun processUpdates(coroutineScope: CoroutineScope, updateChannel: Channel<ExecutedStep>) {
        coroutineScope.launch {
            for (executedStep in updateChannel) {
                val currentTimeOffset = timeOffset(startDateTime, LocalDateTime.now())
                frame.title = "$FRAME_TITLE_PREFIX (at $currentTimeOffset milliseconds)"

                stepsPanel.updateExecutedStep(executedStep)
            }
        }
    }
}


fun timeOffset(startTime: LocalDateTime, endTime: LocalDateTime?): Long {
    return if (endTime != null) ChronoUnit.MILLIS.between(startTime, endTime) else 0
}
