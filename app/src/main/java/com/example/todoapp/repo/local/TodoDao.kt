package com.example.todoapp.repo.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.repo.local.model.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    fun getTodos(): LiveData<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodos(todos: List<TodoEntity>)

    @Delete
    suspend fun deleteTodos(todos: List<TodoEntity>)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

}