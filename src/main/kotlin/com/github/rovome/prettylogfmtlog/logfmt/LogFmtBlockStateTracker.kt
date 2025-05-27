package com.github.rovome.prettylogfmtlog.logfmt

class LogFmtBlockStateTracker {

  private enum class State {
    OUTSIDE_BLOCK,
    INSIDE_BLOCK,
    INSIDE_QUOTED_MULTILINE_VALUE
  }

  private var currentState: State = State.OUTSIDE_BLOCK

  private val blockItemIndentString = "    " // 4 spaces
  private val blockItemIndentLength = blockItemIndentString.length

  // Regex for "    key = value_string" (allowing . and - in keys)
  private val keyValuePattern = Regex("""^(${Regex.escape(blockItemIndentString)})([\w.-]+)\s*=\s*(.*)$""")

  fun resetState() {
    currentState = State.OUTSIDE_BLOCK
  }

  fun shouldFoldLine(line: String): Boolean {
    when (currentState) {
      State.OUTSIDE_BLOCK -> {
        if (line.trim() == "[") {
          currentState = State.INSIDE_BLOCK
          return true
        }
        return false
      }

      State.INSIDE_BLOCK -> {
        if (line.trim() == "]") {
          currentState = State.OUTSIDE_BLOCK
          return true
        }

        val matchResult = keyValuePattern.matchEntire(line)
        if (matchResult != null) { // Line is like "   key = value_string"
          val key = matchResult.groups[2]!!.value
          val valueStringOnLine = matchResult.groups[3]!!.value

          // Check if this is the start of a multi-line quoted error string
          if (key == "error" && valueStringOnLine.startsWith('"') &&
            isEffectivelyUnclosedQuotedStringOnLine(valueStringOnLine)) {
            currentState = State.INSIDE_QUOTED_MULTILINE_VALUE
          }
          // Any matched key-value pair is part of the block
          return true
        }

        // Line is not ']', not a recognized key=value pattern for this block.
        currentState = State.OUTSIDE_BLOCK
        return false
      }

      State.INSIDE_QUOTED_MULTILINE_VALUE -> {
        // Check for the specific "closing quote on its own line", e.g., "   \""
        if (line.trim() == "\""
          && line.startsWith(blockItemIndentString)
          && line.length == blockItemIndentLength + 1) {

          currentState = State.INSIDE_BLOCK // Multi-line quote is now closed
          return true // This closing line is part of the fold
        }

        // Any other line within this state MUST start with at least the block item indent.
        // If not, the error string's formatting is broken or unexpectedly terminated.
        if (!line.startsWith(blockItemIndentString)) {
          currentState = State.OUTSIDE_BLOCK
          return false
        }

        // The line has the necessary block indent. It's part of the multi-line error string.
        // Now, check if this line *contains* the closing quote for the error string.
        // The part of the string that represents the value starts after blockItemIndentString.
        val valueContinuation = line.substring(blockItemIndentLength)
        if (lineEffectivelyEndsWithUnescapedQuote(valueContinuation.trimEnd())) {
          // This line (e.g., "   ...last part of error\"") closes the quote.
          currentState = State.INSIDE_BLOCK
        }
        // else, it's a continuation line within the error, state remains INSIDE_QUOTED_MULTILINE_VALUE.
        return true
      }
    }
  }

  private fun isEffectivelyUnclosedQuotedStringOnLine(s: String): Boolean {
    if (!s.startsWith('"')) {
      return false
    }

    // An empty quoted string "" is closed, " " is closed if s is " \" ".
    // A string like "\"" (just a quote) is unclosed on its own.
    if (s == "\"") {
      // A single quote character means it's unclosed here.
      return true
    }

    var isEscaped = false
    var effectiveQuoteCount = 0
    for (char in s) {
      if (isEscaped) {
        isEscaped = false
        continue
      }
      when (char) {
        '\\' -> isEscaped = true
        '"' -> effectiveQuoteCount++
      }
    }
    // If the last char was an escape, it implies the string is 'open' waiting for the escaped char,
    // or if it was an escape for a quote at the very end that wasn't completed.
    // However, the primary concern is odd number of quotes.
    return effectiveQuoteCount % 2 != 0
  }

  private fun lineEffectivelyEndsWithUnescapedQuote(text: String): Boolean {
    if (!text.endsWith('"')) {
      return false
    }
    if (text == "\"") {
      // Handles cases where content is just the quote itself
      return true
    }

    var backslashCount = 0
    var i = text.length - 2
    while (i >= 0 && text[i] == '\\') {
      backslashCount++
      i--
    }
    return backslashCount % 2 == 0 // Even number of backslashes means quote is not escaped
  }
}