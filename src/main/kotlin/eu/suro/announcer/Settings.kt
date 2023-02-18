package eu.suro.announcer

import eu.suro.announcer.objects.Message

class Settings {



    companion object{
        var messages: List<Message> = ArrayList()

        @JvmStatic
        fun globalSetup(){
            messages = ArrayList()
            AnnouncerMain.instance.announcerConfig.messages.forEach{
                val message = Message(it.lines)
                message.showMode = Message.ShowMode.valueOf(it.snowmode)
                if( message.showMode != Message.ShowMode.ALLSERVERS){
                    message.servers = it.servers
                }
            }
        }

    }

}