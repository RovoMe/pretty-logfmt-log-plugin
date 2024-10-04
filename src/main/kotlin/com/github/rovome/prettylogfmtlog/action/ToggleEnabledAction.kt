package com.github.rovome.prettylogfmtlog.action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.DumbAwareToggleAction
import com.github.rovome.prettylogfmtlog.service.EphemeralStateService
import com.intellij.openapi.actionSystem.ActionUpdateThread

class ToggleEnabledAction : DumbAwareToggleAction() {
    override fun isSelected(e: AnActionEvent): Boolean {
        val consoleView = e.getData(LangDataKeys.CONSOLE_VIEW) ?: return false
        val project = e.project ?: return false
        val service = project.service<EphemeralStateService>()
        return service.isEnabled(consoleView)
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        thisLogger().debug("setSelected: $state")
        val consoleView = e.getData(LangDataKeys.CONSOLE_VIEW) ?: return
        val project = e.project ?: return
        val service = project.service<EphemeralStateService>()
        service.setEnabled(consoleView, state)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}