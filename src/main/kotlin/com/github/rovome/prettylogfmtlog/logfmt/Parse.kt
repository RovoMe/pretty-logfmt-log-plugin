package com.github.rovome.prettylogfmtlog.logfmt

fun parseLogLine(logLine: String): Map<String, String>? {
    // Create a mutable map to store key-value pairs
    val result = mutableMapOf<String, String>()

    var i = 0
    val length = logLine.length

    while (i < length) {
        // Skip any leading spaces
        while (i < length && logLine[i].isWhitespace()) {
            i++
        }

        // Extract the key
        val keyStart = i
        while (i < length && logLine[i] != '=' && !logLine[i].isWhitespace()) {
            i++
        }
        if (i >= length || logLine[i] != '=') {
            // If no '=' found, break (invalid logLine format)
            break
        }

        val key = logLine.substring(keyStart, i).trim()

        i++ // Skip '=' character

        // Extract the value
        val value = StringBuilder()

        // Check if value starts with a quote or a square bracket
        if (i < length && logLine[i] == '"') {
            i++ // Skip opening quote
            value.append('"') // Include the opening quote
            while (i < length) {
                if (logLine[i] == '\\' && i + 1 < length && logLine[i + 1] == '"') {
                    // Handle escaped quote
                    value.append("\\\"")
                    i += 2
                } else if (logLine[i] == '"') {
                    // Closing quote found
                    value.append('"') // Include the closing quote
                    i++
                    break
                } else {
                    value.append(logLine[i])
                    i++
                }
            }
        } else if (i < length && logLine[i] == '[') {
            value.append('[') // Include the opening bracket
            i++ // Skip the opening square bracket
            while (i < length) {
                if (logLine[i] == '\\' && i + 1 < length && logLine[i + 1] == ']') {
                    // Handle escaped closing bracket
                    value.append(']')
                    i += 2
                } else if (logLine[i] == ']') {
                    // Include the closing square bracket and break
                    value.append(']')
                    i++
                    break
                } else {
                    value.append(logLine[i])
                    i++
                }
            }
        } else {
            // Read until a space is encountered
            while (i < length && !logLine[i].isWhitespace()) {
                value.append(logLine[i])
                i++
            }
        }

        // Add the key-value pair to the map
        result[key] = value.toString()
    }

    // return the map of parsed properties or null if it's empty
    return result.takeIf { it.isNotEmpty() }
}