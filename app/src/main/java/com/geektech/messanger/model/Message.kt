package com.geektech.messanger.model

import java.io.Serializable

data class Message(
    var senderUid: Any?,
    var receiverUid: String,
    var message: String,
    var time: Long = System.currentTimeMillis()
): Serializable
