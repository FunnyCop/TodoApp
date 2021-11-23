package com.example.todoapp.repo

import androidx.lifecycle.LiveData
import com.example.todoapp.repo.local.TodoDao
import com.example.todoapp.repo.local.model.TodoEntity

class TodoRepo(private val todoDao: TodoDao) {

    val todos: LiveData<List<TodoEntity>> = todoDao.getTodos()

    /** Add */
    suspend fun addTodo(todo: TodoEntity) = todoDao.addTodo(todo)

    /** Add Multiple */
    suspend fun addTodos(todos: List<TodoEntity>) = todoDao.addTodos(todos)

    /** Delete */
    suspend fun deleteTodo(todo: TodoEntity) = todoDao.deleteTodo(todo)

    /** Delete Multiple */
    suspend fun deleteTodos(todos: List<TodoEntity>) = todoDao.deleteTodos(todos)

}