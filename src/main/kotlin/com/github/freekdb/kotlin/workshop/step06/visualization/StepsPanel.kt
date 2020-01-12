package com.github.freekdb.kotlin.workshop.step06.visualization

import com.github.freekdb.kotlin.workshop.step06.main.ExecutedStep
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.time.LocalDateTime
import javax.swing.JPanel
import javax.swing.SwingUtilities

class StepsPanel(private val threadCount: Int, private val startDateTime: LocalDateTime) : JPanel() {
    private val top = 40
    private val left = 40
    private val middle = left + 640
    private val columnWidth = 72
    private val timeDivisor = 20

    private val executedSteps = mutableListOf<ExecutedStep>()

    init {
        background = Color.BLACK
    }

    fun updateExecutedStep(executedStep: ExecutedStep) {
        executedSteps += executedStep

        SwingUtilities.invokeAndWait {
            repaint()
        }

        // takeScreenshot()
    }

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)

        if (graphics != null) {
            graphics.font = Font("Sans Serif", Font.BOLD, 28)

            paintThreadHeaders(graphics)
            paintStepsInTime(graphics)
            paintProgressTaskSteps(graphics)
        }
    }

    private fun paintThreadHeaders(graphics: Graphics) {
        for (threadNumber in 1..threadCount) {
            val x = left + (threadNumber - 1) * columnWidth
            val y = top

            graphics.color = Color.WHITE
            graphics.fill3DRect(x, y, 60, 40, true)

            graphics.color = Color.BLACK
            graphics.drawString("T$threadNumber", x + 10, y + 32)
        }
    }

    private fun paintStepsInTime(graphics: Graphics) {
        executedSteps
            .filter { it.stepNumber > 0 }
            .forEach {
                val startTime = timeOffset(startDateTime, it.startTime)
                val endTime = timeOffset(startDateTime, it.endTime)
                val x = left + (it.threadNumber - 1) * columnWidth
                val y = top + 70 + (startTime / timeDivisor).toInt()
                val height = ((endTime - startTime - 28) / timeDivisor).toInt()
                val colorIndex = it.taskId - 1

                graphics.color = BASIC_COLORS[colorIndex]
                graphics.fill3DRect(x, y, 60, height, true)

                graphics.color = Color.BLACK
                graphics.drawString(it.stepNumber.toString(), x + 20, y + height / 2 + 10)
            }

    }

    private fun paintProgressTaskSteps(graphics: Graphics) {
        executedSteps
            .filter { it.stepNumber > 0 }
            .forEach {
                val x = middle + (it.taskId - 1) * columnWidth
                val y = top + 560 + it.stepNumber * 40
                val colorIndex = it.taskId - 1

                graphics.color = BASIC_COLORS[colorIndex]
                graphics.fill3DRect(x, y, 60, 32, true)

                graphics.color = Color.BLACK
                graphics.drawString(it.stepNumber.toString(), x + 20, y + 28)
            }
    }
}
