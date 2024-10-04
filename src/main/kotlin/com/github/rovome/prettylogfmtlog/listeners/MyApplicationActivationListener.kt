package com.github.rovome.prettylogfmtlog.listeners

import com.github.rovome.prettylogfmtlog.MyBundle
import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.wm.IdeFrame

internal class MyApplicationActivationListener : ApplicationActivationListener {

    override fun applicationActivated(ideFrame: IdeFrame) {
        thisLogger().debug(MyBundle.message("applicationActivated"))
    }
}
