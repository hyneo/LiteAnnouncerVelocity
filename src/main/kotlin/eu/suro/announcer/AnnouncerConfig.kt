package eu.suro.announcer

import de.exlll.configlib.Comment
import de.exlll.configlib.Configuration

@Configuration
class AnnouncerConfig {
    @Comment("Настройки")
    var settings = Settings()

    @Comment("Сообщения")
    var messages = listOf<Message>(
        Message("ONLYFOR", listOf("exampleServer"), listOf("HNTOP???"))
    )

    class Settings {
        @Comment("Задержка между сообщениями")
        var interval: Int = 60
    }

    data class Message(var snowmode: String, var servers: List<String>, var lines: List<String>)
}



