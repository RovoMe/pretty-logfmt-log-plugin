package com.github.rovome.prettylogfmtlog.logentry

private val stackTraceNodeExtractors: List<NodeExtractor> = listOf(
    { it["stack_trace"] },
    { it["exception"] },
    { it["error.stack_trace"] },
    { it["err"] },
    { it["stack"] },
    { it["@x"] },
    { it["Exception"] },
)

fun extractStackTrace(node: Map<String, String>): String? {
    return sanitize(stackTraceNodeExtractors.firstNotNullOfOrNull { it(node) })
}