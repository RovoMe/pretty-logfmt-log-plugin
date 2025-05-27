package com.github.rovome.prettylogfmtlog.logfmt

fun prettyPrintLogfmt(node: Map<String, String>): String {
    if (node.isEmpty()) {
        return ""
    }
    val baseIndent = "    " // 4 spaces
    val errorContentExtraIndent = "    "

    // Join key-value pairs, indented by four spaces, each on a new line
    val formattedLines = node.entries.map { (key, value) ->
        if (key == "error") {
            val coreErrorContent = if (value.startsWith('"') && value.endsWith('"') && value.length >= 2) {
                value.substring(1, value.length - 1)
            } else {
                value // Use as-is if not quoted, though error messages with newlines should be.
            }

            val sb = StringBuilder()
            coreErrorContent.lines().forEachIndexed { index, line ->
                if (index == 0) {
                    // Line 1 for this entry: "    error = \"" (starts the quote, adds a newline)
                    sb.append(baseIndent)
                        .append(key)
                        .append(" = \"$line")
                } else {
                    sb.append("\n")
                    sb.append(baseIndent)
                        .append(errorContentExtraIndent)
                        .append(line)
                }
            }

            sb.append("\"")
            sb.toString()
        } else {
            "$baseIndent$key = $value"
        }
    }

    // Return the formatted string enclosed in brackets, each on their own lines
    return "[\n${formattedLines.joinToString("\n")}\n]\n"
}