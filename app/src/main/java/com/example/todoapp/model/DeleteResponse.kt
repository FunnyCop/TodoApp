package com.example.todoapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteResponse(

    val message: String

)
