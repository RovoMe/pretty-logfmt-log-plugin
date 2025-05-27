package com.github.rovome.prettylogfmtlog.console

import com.github.rovome.prettylogfmtlog.logfmt.LogFmtBlockStateTracker
import com.github.rovome.prettylogfmtlog.service.EphemeralStateService
import com.intellij.execution.ConsoleFolding
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.jetbrains.rd.util.ConcurrentHashMap

class MyConsoleFolding : ConsoleFolding() {
    private var consoleView: ConsoleView? = null

    // Companion object to manage state trackers per ConsoleView
    companion object {
        private val consoleTrackers = ConcurrentHashMap<ConsoleView, LogFmtBlockStateTracker>()

        private fun getTracker(consoleView: ConsoleView): LogFmtBlockStateTracker {
            return consoleTrackers.computeIfAbsent(consoleView) {
                LogFmtBlockStateTracker()
            }
        }

        fun clearTrackerForConsole(consoleView: ConsoleView) {
            consoleTrackers.remove(consoleView)
        }
    }

    override fun getPlaceholderText(project: Project, lines: List<String>): String {
        return "[...]"
    }

    override fun shouldFoldLine(project: Project, line: String): Boolean {
        val currentConsoleView = this.consoleView?: return false

        if (!isEnabled(project, currentConsoleView)) {
            getTracker(currentConsoleView).resetState()
            return false
        }

        val tracker = getTracker(currentConsoleView)
        return tracker.shouldFoldLine(line)
    }

    override fun isEnabledForConsole(consoleView: ConsoleView): Boolean {
        // This method "isEnabledForConsole" is not for storing consoleView, but we use it for that
        // purpose because there is no other way to get consoleView reference in "shouldFoldLine"
        // method.
        this.consoleView = consoleView
        return super.isEnabledForConsole(consoleView)
    }

    private fun isEnabled(project: Project, console: ConsoleView): Boolean {
        val service = project.service<EphemeralStateService>()
        return service.isEnabled(console)
    }

    override fun shouldBeAttachedToThePreviousLine(): Boolean {
        // attach [...] to the end of the log line instead of to new line
        return true
    }
}