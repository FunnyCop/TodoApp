package com.example.todoapp.util

import android.text.Editable
import com.example.todoapp.model.TodoResponse
import com.example.todoapp.repo.local.model.TodoEntity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

val TextInputEditText.stringText get() = if (this.text == null) "" else this.text.toString()

fun List<TodoResponse>.convert(): List<TodoEntity> {

    val todoList = mutableListOf<TodoEntity>()

    this.forEach { todoList.add(TodoEntity(

        it.id,
        it.title,
        it.description,
        it.completed,
        it.userId,
        it.date,
        it.updatedAt

    )) }

    return todoList

}

fun TodoResponse.convert() = TodoEntity(id, title, description, completed, userId, date, updatedAt)

fun TodoEntity.convert() = TodoResponse(id, title, description, completed, userId, date, updatedAt)

val String.editable: Editable get() = Editable.Factory.getInstance().newEditable(this)

inline fun MaterialButton.onClick(

    crossinline block: (MaterialButton) -> Unit

) = this.setOnClickListener { block(this) }