package com.example.ui.Model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatModel(
var msg: String? = "",
var sender: String? = "",
var time:String?=""
)
