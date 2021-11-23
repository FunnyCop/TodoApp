package com.example.todoapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(

    val message: String,
    val token: String

)