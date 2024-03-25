package com.example.chatapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.models.Chat
import com.example.chatapp.utils.Constants.BROADCAST
import com.example.chatapp.utils.Constants.NEW_MESSAGE
import com.example.chatapp.utils.Constants.SOCKET_URL
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketManager {

    private var socket:Socket? = null
    private var _onNewChat = MutableLiveData<Chat>()
    val onNewChat:LiveData<Chat> get() =  _onNewChat

    init {
        try {
            socket = IO.socket(SOCKET_URL)
            socket?.connect()

            registerOnNewChat()
        }catch (e:URISyntaxException){
            e.printStackTrace()
        }
    }

    private fun registerOnNewChat() {
        socket?.on(BROADCAST){ args->
            args?.let { value->
                if(value.isNotEmpty()){
                    val data = value[0]
                    Log.d("DATA_DEBUG","$data")
                    if(data.toString().isNotEmpty()){
                        val chat = Gson().fromJson(data.toString(),Chat::class.java)
                        _onNewChat.postValue(chat)
                    }
                }
            }
        }
    }

    fun disconnectSocket(){
        socket?.disconnect()
        socket?.off()
    }

    fun emitChat(chat:Chat){
        val jsonStr = Gson().toJson(chat, Chat::class.java)
        socket!!.emit(NEW_MESSAGE,jsonStr)
    }

}