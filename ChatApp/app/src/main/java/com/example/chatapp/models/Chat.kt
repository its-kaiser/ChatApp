package com.example.chatapp.models

data class Chat(
    val userName:String,
    val text:String,
    var isSender:Boolean = false
)
