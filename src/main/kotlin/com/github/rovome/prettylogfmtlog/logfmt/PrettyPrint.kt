package com.github.rovome.prettylogfmtlog.logfmt

fun prettyPrintLogfmt(node: Map<String, String>): String {
    if (node.isEmpty()) {
        return ""
    }
    // Join key-value pairs, indented by four spaces, each on a new line
    val formattedEntries = node.entries.joinToString("\n") {
        (key, value) -> "    $key = $value"
    }
    // Return the formatted string enclosed in brackets, each on their own lines
    return "[\n$formattedEntries\n]\n"
}

// The RegEx below is responsible for determining the folding area of a LOGFTM formatted log line.
//
// This plugin will modify the actual log line in the IntelliJ console by first printing the
// timestamp, the log level and the actual message to log and then add a '[' bracket on a new line
// which acts as folding start symbol for the data portion of the LOGFMT line. Afterward, each
// key-value pair is added on a separate line indenting the pair by 4 space characters and
// separating key and value a bit for better readability. The trailing ']' character on a separate
// line then defines the end of the folding area for the processed log line. The RegEx pattern below
// therefore returns true when either a line in the IntelliJ console includes '[' or  ']' on
// separate lines or when a line starts with four blank spaces and then contains a 'key = ...'
// portion.
//
// Sample:
// ["2024-10-04T10:00:00.000"] INFO: "This is a sample message"
// [
//     time = "2024-10-04T10:00:00.000"
//     level = info
//     thread = main
//     package = org.acme
//     module = SomeClass
//     msg = "This is a sample message"
// ]
//
// The RegEx will determine which part to include in the folding area and thus initially hide. Any
// log line not matching the RegEx pattern will not be included in the final output and thus shown.
// Folded log lines are initially hidden and can be toggled via clicking on the added '>' symbol at
// the start of the log line or at the '[...]' symbol at the end of it. These symbols are only
// visible in IntelliJ and are not actually part of the log itself.
//
// ^                                                 -> start of line
// (?:                                               -> start of no-capture group
// \[                                                -> start of expanded node and nothing afterward
// |]                                                -> end of expanded node and nothing afterward
// | {4}[\w._]+ = ("((?:[^"\\]|\\.)* *)"|\S+)        -> 'key = value' part in expanded node
// )                                                 -- end of no-capture group
// $                                                 -> the end of the line
private val prettyLogfmtPartRegex = Regex("""^(?:\[|]| {4}[\w._]+ = ("((?:[^"\\]|\\.)* *)"|\S+))$""")

fun isPartOfPrettyLogfmt(line: String): Boolean {
    return prettyLogfmtPartRegex.containsMatchIn(line)
}