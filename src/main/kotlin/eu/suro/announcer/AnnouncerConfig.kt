package eu.suro.announcer

import de.exlll.configlib.Comment
import de.exlll.configlib.Configuration

@Configuration
class AnnouncerConfig {
    @Comment("Настройки")
    var settings = SettingsConfig()

    @Comment("Сообщения")
    var messages = listOf<Message>(
        Message("ONLYFOR", listOf("exampleServer"), listOf("HNTOP???"))
    )
}

@Configuration
class SettingsConfig {
    @Comment("Задержка между сообщениями")
    var interval: Int = 60
}

@Configuration
class Message {
    lateinit var snowmode: String
    lateinit var servers: List<String>
    lateinit var lines: List<String>

    constructor(){

    }

    constructor(snowmode: String, servers: List<String>, lines: List<String>){
        this.snowmode = snowmode
        this.servers = servers
        this.lines = lines
    }
}
