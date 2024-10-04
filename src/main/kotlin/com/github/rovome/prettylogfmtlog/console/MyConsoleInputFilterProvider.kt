package com.github.rovome.prettylogfmtlog.console

import com.intellij.execution.filters.ConsoleDependentInputFilterProvider
import com.intellij.execution.filters.InputFilter
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.intellij.psi.search.GlobalSearchScope
import com.github.rovome.prettylogfmtlog.logfmt.parseLogLine
import com.github.rovome.prettylogfmtlog.logfmt.prettyPrintLogfmt
import com.github.rovome.prettylogfmtlog.logentry.*
import com.github.rovome.prettylogfmtlog.service.EphemeralStateService
import com.intellij.execution.ui.ConsoleView
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// We use ConsoleDependentInputFilterProvider instead of ConsoleInputFilterProvider because we need
// to access ConsoleView and Project in the filter.
class MyConsoleInputFilterProvider : ConsoleDependentInputFilterProvider() {
    override fun getDefaultFilters(
        consoleView: ConsoleView,
        project: Project,
        scope: GlobalSearchScope
    ): MutableList<InputFilter> {
        thisLogger().debug("getDefaultFilters")
        return mutableListOf(MyConsoleInputFilter(consoleView, project))
    }
}

private val zoneId = ZoneId.systemDefault()
private val timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

class MyConsoleInputFilter(
    private val consoleView: ConsoleView,
    private val project: Project
) : InputFilter {

    override fun applyFilter(
            text: String,
            contentType: ConsoleViewContentType
    ): MutableList<Pair<String, ConsoleViewContentType>>? {
        thisLogger().debug("contentType: $contentType, applyFilter: $text")
        if (!isEnabled()) {
            return null
        }
        val node = parseLogLine(text) ?: return null

        val timestamp = extractTimestamp(node)
        val level = extractLevel(node)
        val message = extractMessage(node)
        val stackTrace = extractStackTrace(node)

        if (timestamp == null && level == null && message == null) {
            // we do not have a valid LOGFMT log line
            return mutableListOf(Pair(text, contentType))
        }

        // .trimEnd('\n') is necessary because of the following reasons:
        // - When stackTrace is null or empty, we don't want to add an extra newline.
        // - When stackTrace ends with a newline, trimming the last newline makes a folding marker
        //   look better.
        val coloredMessage = "$level: $message\n${stackTrace ?: ""}".trimEnd('\n')

        val logfmtString = prettyPrintLogfmt(node)
        return mutableListOf(
                Pair("[${timestamp?.format(zoneId, timestampFormatter)}] ", contentType),
                Pair(coloredMessage, contentTypeOf(level, contentType)),
                Pair(" \n$logfmtString", contentType),
        )
    }

    private fun isEnabled(): Boolean {
        val service = project.service<EphemeralStateService>()
        return service.isEnabled(consoleView)
    }
}

private fun contentTypeOf(level: Level?, inputContentType: ConsoleViewContentType): ConsoleViewContentType {
    return when (level) {
        Level.TRACE -> ConsoleViewContentType.LOG_VERBOSE_OUTPUT
        Level.DEBUG -> ConsoleViewContentType.LOG_DEBUG_OUTPUT
        Level.INFO -> ConsoleViewContentType.LOG_INFO_OUTPUT
        Level.WARN -> ConsoleViewContentType.LOG_WARNING_OUTPUT
        Level.ERROR, Level.FATAL -> ConsoleViewContentType.LOG_ERROR_OUTPUT
        else -> inputContentType
    }
}