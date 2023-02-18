package eu.suro.announcer

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.Component

class AnnouncerCommand : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        if(source is Player){
            return
        }
        val time = System.currentTimeMillis()
        AnnouncerMain.runTask.cancel()
        AnnouncerMain.reloadConfig()
        Settings.globalSetup()
        AnnouncerMain.runTask.start()
        source.sendMessage(Component.text("Конфиг перезапустился за "+ (System.currentTimeMillis() - time)))
    }
    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
        return invocation.source().hasPermission("announcer.admin")
    }
}