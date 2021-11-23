package com.example.todoapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoResponse(

    val id: Int,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val userId: Int,
    val date: String,
    val updatedAt: String

)