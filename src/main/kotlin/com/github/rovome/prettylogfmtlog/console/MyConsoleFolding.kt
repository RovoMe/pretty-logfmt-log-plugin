package com.github.rovome.prettylogfmtlog.console

import com.intellij.execution.ConsoleFolding
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.rovome.prettylogfmtlog.logfmt.isPartOfPrettyLogfmt
import com.github.rovome.prettylogfmtlog.service.EphemeralStateService

class MyConsoleFolding : ConsoleFolding() {
    private var consoleView: ConsoleView? = null

    override fun getPlaceholderText(project: Project, lines: List<String>): String {
        return "[...]"
    }

    override fun shouldFoldLine(project: Project, line: String): Boolean {
        if (!isEnabled(project)) {
            return false
        }
        return isPartOfPrettyLogfmt(line)
    }

    override fun isEnabledForConsole(consoleView: ConsoleView): Boolean {
        // This method "isEnabledForConsole" is not for storing consoleView, but we use it for that
        // purpose because there is no other way to get consoleView reference in "shouldFoldLine"
        // method.
        this.consoleView = consoleView
        return true
    }

    private fun isEnabled(project: Project): Boolean {
        val service = project.service<EphemeralStateService>()
        val consoleView = this.consoleView ?: return false
        return service.isEnabled(consoleView)
    }

//    override fun shouldBeAttachedToThePreviousLine(): Boolean {
//        return false
//    }
}