package eu.suro.announcer


import com.velocitypowered.api.scheduler.ScheduledTask
import eu.suro.announcer.objects.Message
import java.time.Duration

class AnnouncerTask : Runnable {
    private var currentMessageNumber = 0
    private var runned = false
    companion object{
        private lateinit var task: ScheduledTask
    }

    fun start() {
        task = AnnouncerMain.instance.proxyServer.scheduler
            .buildTask(AnnouncerMain.instance, this)
            .delay(Duration.ofSeconds(5))
            .repeat(Duration.ofSeconds(AnnouncerMain.instance.announcerConfig.settings.interval.toLong()))
            .schedule()
        runned = true
    }

    override fun run() {
        try {
            val message: Message = if(this.currentMessageNumber < Settings.messages.size){
                Settings.messages[this.currentMessageNumber]
            }else{
                Settings.messages[0]
            }
            sendMessage(message)
            this.currentMessageNumber = message.number
        }catch (e: Exception){
            println("[Announcer] Ошибка отправки сообщения игрокам")
            e.printStackTrace()
        }
    }
    private fun sendMessage(message: Message) {
        when (message.showMode) {
            Message.ShowMode.ALLSERVERS -> AnnouncerMain.instance.proxyServer.allPlayers.forEach(message::sendFor)
            Message.ShowMode.ONLYFOR ->  AnnouncerMain.instance.proxyServer.allPlayers.forEach { p ->
                if (p.currentServer.isPresent && message.servers
                        .contains(p.currentServer.get().serverInfo.name)
                ) {
                    message.sendFor(p)
                }
            }
            Message.ShowMode.EXCEPTFOR ->  AnnouncerMain.instance.proxyServer.allPlayers.forEach { p ->
                if (p.currentServer.isPresent && !message.servers
                        .contains(p.currentServer.get().serverInfo.name)
                ) {
                    message.sendFor(p)
                }
            }
        }
    }

    fun restart() {
        if (runned) {
            currentMessageNumber = 0
            task.cancel()
            AnnouncerMain.instance.proxyServer.scheduler
                .buildTask(AnnouncerMain.instance, this)
                .delay(Duration.ofSeconds(5))
                .repeat(Duration.ofSeconds(AnnouncerMain.instance.announcerConfig.settings.interval.toLong()))
                .schedule()
        }
    }

    fun cancel() {
        task.cancel()
        currentMessageNumber = 0
        runned = false
    }
}