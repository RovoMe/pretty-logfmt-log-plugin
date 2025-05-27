package com.github.rovome.prettylogfmtlog.logfmt

import junit.framework.TestCase

class FoldingTest : TestCase() {
    private data class Param(
            val description: String,
            val creator: (MutableMap<String, String>) -> Unit
    )

    val tracker = LogFmtBlockStateTracker()

    private val params = listOf(
        Param("Simple object") {
            it["level"] = "info"
            it["time"] = "2024-09-06T12:00:00Z"
            it["user"] = "\"john doe\""
            it["msg"] = "\"Test message\""
            it["status"] = "success"
        },
        Param("line with issue") {
            it["time"] = "\"2024-09-09T17:39:41.174\""
            it["level"] = "info"
            it["msg"] = "\"Set default timezone to sun.util.calendar.ZoneInfo[id=\\\\\\\"UTC\\\\\\\",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null]\""
            it["package"] = "com.ecosio.app"
            it["container"] = ""
            it["nodeHost"] = ""
            it["node"] = ""
            it["region"] = "eu-central-1"
            it["thread"] = "main"
        }
    )

    fun testFolding() {
        params.forEach { param ->
            // Create a local mutable map
            val map = mutableMapOf<String, String>()
            // Use the creator function from Param to modify the local map
            param.creator(map)
            // use the filled map to run the parameterized test
            assertAllLinesFolded(param.description, map)
        }
    }

    private fun assertAllLinesFolded(description: String, node: Map<String, String>) {
        val log = prettyPrintLogfmt(node)
        println(log)
        val lines = log.lines()
        assertTrue("[$description] Lines should not be empty", lines.isNotEmpty())
        lines.forEachIndexed { index, line ->
            if (index < lines.size - 1) {
                assertTrue(
                    "[$description] Line $index should be folded",
                    tracker.shouldFoldLine(line)
                )
            } else {
                // last line consists of an empty string due to the pretty printing adding a
                // trailing \n to the message as otherwise the closing ] would be shown on the start
                // of the next log line
                assertFalse("[$description] Line $index should not be folded",
                    tracker.shouldFoldLine(line)
                )
            }
        }
    }
}