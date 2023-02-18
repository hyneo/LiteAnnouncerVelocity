package eu.suro.announcer

import com.google.inject.Inject
import com.velocitypowered.api.command.CommandMeta
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import de.exlll.configlib.YamlConfigurations
import java.io.File
import java.nio.file.Path


@Plugin(id = "announcer", name = "LiteAnnouncer", version = "1.0.0", authors = ["Suro"])
class AnnouncerMain @Inject constructor(val proxyServer: ProxyServer, @DataDirectory val dataDirectory: Path){

    lateinit var announcerConfig: AnnouncerConfig

    companion object{
        val runTask = AnnouncerTask()
        @JvmStatic
        lateinit var instance: AnnouncerMain
        fun reloadConfig(){
            val configFile = File(instance.dataDirectory.toFile(), "config.yml")
            if(!configFile.exists()){
                YamlConfigurations.save(configFile.toPath(), AnnouncerConfig::class.java, AnnouncerConfig())
            }
            instance.announcerConfig = YamlConfigurations.load(configFile.toPath(), AnnouncerConfig::class.java)
        }
    }

    init {
        instance = this
        reloadConfig()
    }

    @Subscribe
    fun onInit(event: ProxyInitializeEvent){
        Settings.globalSetup()
        runTask.start()
        val commandMeta: CommandMeta =
            proxyServer.commandManager.metaBuilder("announcer") // This will create a new alias for the command "/test"
                // with the same arguments and functionality
                .plugin(this)
                .build()
        proxyServer.commandManager.register(commandMeta, AnnouncerCommand())
    }
}