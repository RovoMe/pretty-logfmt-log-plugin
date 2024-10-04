package com.github.rovome.prettylogfmtlog.logfmt

import junit.framework.TestCase
class PrettyPrintTest : TestCase() {
    val prettyPrintIndicators = listOf(
            "level=info time=2024-09-06T12:00:00Z msg=\"Test message\" [...]",
            "[",
            "]",
            "  key = value",
            "    anotherKey = \"another value\"",
            "    msg = \"Properties location [classpath:build.properties] not resolvable: class path resource [build.properties] cannot be opened because it does not exist\""
    )

    val standardLogfmtLogLineIndicators = listOf(
            "key=value",
            "key1=value1 key2=value2",
            "level=info time=2024-09-06T12:00:00Z user=\"john doe\" msg=\"Test message\" status=success"
    )

    fun testLogLinesThatShouldMatchAPrettyPrintedLogLine() {
        prettyPrintIndicators.forEach {
            assertTrue("Expected match for input: '$it'", isPartOfPrettyLogfmt(it))
        }
    }

    fun testLogLinesThatDoNotMatchPrettyPrintedLogLInes() {
        standardLogfmtLogLineIndicators.forEach {
            assertFalse("Expected no match for input: '$it'", isPartOfPrettyLogfmt(it), )
        }
    }

    fun testPrettyPrinting() {
        val map = mutableMapOf<String, String>()
        map["level"] = "info"
        map["time"] = "2024-09-06T12:00:00Z"
        map["msg"] = "Test message"
        map["status"] = "success"

        val result = prettyPrintLogfmt(map)
        assertEquals("""[
    level = info
    time = 2024-09-06T12:00:00Z
    msg = Test message
    status = success
]
""", result)
    }
}