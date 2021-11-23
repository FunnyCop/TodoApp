package com.example.todoapp.repo.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "todos")
data class TodoEntity(

    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val userId: Int,
    val date: String,
    val updatedAt: String

): Parcelable
