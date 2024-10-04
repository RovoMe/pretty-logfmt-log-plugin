package com.github.rovome.prettylogfmtlog.logentry

private val messageKeys = listOf("message", "msg", "error.message", "@m", "RenderedMessage")

fun extractMessage(node: Map<String, String>): String? {
    return sanitize(messageKeys.firstNotNullOfOrNull { node[it] })
}

fun sanitize(line: String?): String? {
    return line?.
            replace("\\\"", "\"")?.
            replace("\\t", "\t")?.
            replace("\\\\n", "\n")?.
            replace("\\n", "\n")?.
            replace("\\r", "\r")?.
            replace("\\b", "\b")
}