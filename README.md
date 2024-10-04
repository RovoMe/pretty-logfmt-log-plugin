# <img src="src/main/resources/META-INF/pluginIcon.svg" alt="" width="24" height="24"> Pretty LogFMT Log plugin

![Build](https://github.com/RovoMe/pretty-logfmt-log-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

![Plugin screenshot](media/screenshot_expanded.png)

<!-- Plugin description -->
Pretty LOGFMT Log plugin for IntelliJ Platform makes LOGFMT logs more readable in the console. It 
has the following features:

- **LOGFMT Parsing**: Automatically parses each log line as key/value list and extracts essential 
  log information such as timestamp, log level, and message.
- **Colorful Display**: Displays essential log information in different colors depending on the log 
  level to make it easier to read.
- **Readable Timestamp**: Formats the timestamp in a human-friendly format.
- **Expandable Pretty LOGFMT**: Prints a well-formatted LOGFMT string following the log message. The 
  LOGFMT string is folded by default, but you can expand it when you need to view the full details.
- **Seamless Integration**: Supports various log formats such as Logstash, Bunyan, Pino, log/slog, 
  Cloud Logging, etc. with no additional configuration or software.

This plugin is useful when you are developing a modern system that outputs logs in LOGFMT format. 
You no longer need to switch log formats between production and local development environments.
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "pretty-logfmt-log-plugin"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/RovoMe/pretty-logfmt-log-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Usage

The plugin will reformat any log lines it identifies as LOGFMT log lines and only show the timestamp
of the log, the log level as well as the message of the log line itself. Further properties can be
expanded by either clicking at the folding arrow at the start of the line or on clicking on the 
`[...]` part at the end.

To fold or collapse an expanded log line, click on the arrow at the start of the log line.

## Limitations

JetBrains IDEs provide different kinds of consoles for different run configurations, but this plugin
does not currently support all consoles due to the lack of extension points in the IDE. The console
which does have the following context menu item is not supported.

## Known Issues:

- Very long log lines are broken down after like 2000 characters and the plugin will only format the
  first part then while printing the remaining part as is
- When the `Pretty JSON log` plugin is present and activated it may interfere with the formatting of
  the folding area and thus add further unintended and/or unnecessary folding sections

## How to see the debug log of the plugin

1. <kbd>Help</kbd> > <kbd>Diagnostic Tools</kbd> > <kbd>Debug Log Settings...</kbd>.
2. Add line `#com.github.rovome.prettylogfmlog` to the text area of the dialog and click <kbd>OK</kbd>.
3. Reproduce the issue.
4. <kbd>Help</kbd> > <kbd>Show Log in Finder/Explorer</kbd> to open the log directory.
5. Open the `idea.log` file and find the log of the plugin by searching for `#com.github.rovome.prettylogfmtlog`.

Enabling debug logging may slow down the IDE, so it is recommended to disable it after reproducing 
the issue.

## Acknowledgments

This plugin is a port from the [Pretty JSON Log](https://github.com/orangain/pretty-json-log-plugin)
which itself is inspired by the pino-pretty and bunyan CLI. The great idea behind these tools is 
that applications should write logs in machine-readable format (JSON) and pretty-printing for human 
readability should be done by another tool. I am grateful to the authors of these tools.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
