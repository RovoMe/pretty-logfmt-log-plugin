package com.github.rovome.prettylogfmtlog.logfmt

import junit.framework.TestCase

class ParseTest : TestCase() {

    fun testParseEmptyLogLine() {
        val result = parseLogLine("")
        assertNull(result)
    }

    fun testParseLogLine() {
        val result = parseLogLine("""level=info time=2024-09-06T12:00:00Z user="john doe" msg="Test message" status=success""")
        assertNotNull(result)
        assertEquals(5, result?.size)
        val map = mutableMapOf<String, String>()
        map["level"] = "info"
        map["time"] = "2024-09-06T12:00:00Z"
        map["user"] = "\"john doe\""
        map["msg"] = "\"Test message\""
        map["status"] = "success"
        assertEquals(map, result)
    }

    fun testParseLogLineWithEscapedCharacters() {
        val result = parseLogLine("""level=info time=2024-09-06T12:00:00Z user="john doe" action="login with \"special\" quotes" status=success""")
        assertNotNull(result)
        assertEquals(5, result?.size)
    }

    fun testParseException() {
        val result = parseLogLine("""time="2024-09-05T15:51:28.426" level=warn thread=main package=org.acme module=TestApp msg="Some problem encountered" error="java.lang.Exception: null\n\tat org.acme.TestApp.logError(TestApp.java:94)\n\tat org.acme.TestApp.standardLogger(TestApp.java:34)\n\tat org.acme.TestApp.<init>(TestApp.java:20)\n\tat org.acme.TestApp.main(TestApp.java:16)\n"""")
        assertNotNull(result)
        assertEquals(7, result?.size)
        val map = mutableMapOf<String, String>()
        map["time"] = "\"2024-09-05T15:51:28.426\""
        map["level"] = "warn"
        map["thread"] = "main"
        map["package"] = "org.acme"
        map["module"] = "TestApp"
        map["msg"] = "\"Some problem encountered\""
        map["error"] = "\"java.lang.Exception: null\\n\\tat org.acme.TestApp.logError(TestApp.java:94)\\n\\tat org.acme.TestApp.standardLogger(TestApp.java:34)\\n\\tat org.acme.TestApp.<init>(TestApp.java:20)\\n\\tat org.acme.TestApp.main(TestApp.java:16)\\n\""
        assertEquals(map, result)
    }

    fun testParseBrokenStringEscaping() {
        val result = parseLogLine("""level=info time=2024-09-06T12:00:00Z user="john doe" action=login with \"special\" quotes" status=success""")
        assertNotNull(result)
        assertEquals(4, result?.size)
        val map = mutableMapOf<String, String>()
        map["level"] = "info"
        map["time"] = "2024-09-06T12:00:00Z"
        map["user"] = "\"john doe\""
        map["action"] = "login" // rest is missing
        assertEquals(map, result)
    }
}