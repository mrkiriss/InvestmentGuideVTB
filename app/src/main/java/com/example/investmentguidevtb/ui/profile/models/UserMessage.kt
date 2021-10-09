package com.example.investmentguidevtb.ui.profile.models

import androidx.room.PrimaryKey

data class UserMessage(
    val id: Int,
    val userId: Int, // 1 - me (VIEW_TYPE_MESSAGE_SENT), 2 - app (VIEW_TYPE_MESSAGE_RECEIVED)
    val text: String
)