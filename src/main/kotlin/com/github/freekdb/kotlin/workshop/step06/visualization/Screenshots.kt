package com.github.freekdb.kotlin.workshop.step06.visualization

import java.awt.Rectangle
import java.awt.Robot
import java.io.File
import javax.imageio.ImageIO
import javax.swing.SwingUtilities

private val screenshotsDirectory = createScreenshotsDirectory()
private val guiRobot = Robot()

private var screenshotIndex = 0

private fun createScreenshotsDirectory(): File {
    var nextRunIndex = 1
    var searching = true
    while (searching) {
        if (File(createPathName(nextRunIndex)).exists()) {
            val screenshotList = File(createPathName(nextRunIndex)).list()

            if (screenshotList == null || screenshotList.isNotEmpty()) {
                nextRunIndex++
            } else {
                searching = false
            }
        } else {
            searching = false
        }
    }

    val newScreenshotsDirectory = File(createPathName(nextRunIndex))

    newScreenshotsDirectory.mkdirs()

    return newScreenshotsDirectory
}

private fun createPathName(nextRunIndex: Int) =
        "output/screenshots/run-${nextRunIndex.toString().padStart(2, '0')}/"

@Suppress("unused")
fun takeScreenshot() {
    SwingUtilities.invokeAndWait {
        // println("[${System.currentTimeMillis()}] Waiting before taking next screenshot...")
        Thread.sleep(200)

        screenshotIndex++

        val screenshot = guiRobot.createScreenCapture(Rectangle(0, 0, 1920, 1080))
        val fileName = "screenshot--${screenshotIndex.toString().padStart(2, '0')}.png"
        val outputFile = File(screenshotsDirectory, fileName)
        val result = ImageIO.write(screenshot, "png", outputFile)

        if (!result) {
            println("[${System.currentTimeMillis()}] Taking screenshot $screenshotIndex " +
                    "in file ${outputFile.absolutePath}; result: $result.")
        }
    }
}
