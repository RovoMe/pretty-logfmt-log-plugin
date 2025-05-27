package com.github.rovome.prettylogfmtlog.logfmt

import junit.framework.TestCase

class NotFoldingTest : TestCase() {
    private data class Param(
            val description: String,
            val text: String
    )
    val tracker = LogFmtBlockStateTracker()

    private val params = listOf(
            Param("Log message", "[12:34:56.789] INFO: This is a log message"),
            Param("Empty line", ""),
            Param(
                    "Multiline SQL", """
            SELECT
                id,
                name
            FROM
                table
            WHERE
                id = 1;
        """.trimIndent()
            ),
            Param("Starts with space and double quote", """  "foo" """),
    )

    fun testNotFolding() {
        params.forEachIndexed { index, param ->
            assertLogLineNotFolded(param.description, param.text, index)
        }
    }

    private fun assertLogLineNotFolded(description: String, text: String, idx: Int) {
        val lines = text.lines();
        assertTrue("[$description] Lines of log $idx should not be empty", lines.isNotEmpty())
        lines.forEachIndexed { index, line ->
            assertFalse("[$description] Line $index of log $idx should not be folded", tracker.shouldFoldLine(line))
        }
    }
}