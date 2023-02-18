package eu.suro.announcer.`object`

import com.velocitypowered.api.proxy.Player
import eu.suro.announcer.Settings
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.*
import java.util.function.Consumer

class Message(lines: List<String>) {
    var showMode: ShowMode
    private val lines: MutableList<Component>
    var servers: List<String>
    var number: Int

    init {
        val mm = MiniMessage.miniMessage();
        showMode = ShowMode.ALLSERVERS
        this.lines = ArrayList<Component>()
        servers = ArrayList<String>()
        number = 0
        if (!lines.isEmpty()) {
            number = Settings.messages.size + 1
            lines.forEach(Consumer<String> { line: String ->
                this.lines.add(mm.deserialize(line))
            })
            Settings.messages = Settings.messages.plus(this)
        }
    }

    fun sendFor(player: Player) {
        this.lines.forEach(player::sendMessage)
    }

    fun getLines(): List<Component> {
        return lines
    }

    enum class ShowMode {
        ONLYFOR, EXCEPTFOR, ALLSERVERS
    }
}
