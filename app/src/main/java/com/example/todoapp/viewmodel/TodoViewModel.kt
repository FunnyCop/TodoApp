package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.todoapp.repo.TodoRepo
import com.example.todoapp.repo.local.TodoDatabase
import com.example.todoapp.repo.local.model.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** Room DB View Model */
class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoRepository = TodoRepo(TodoDatabase.getDatabase(application).todoDao())
    val todos = todoRepository.todos

    /** Add */
    fun addTodo(todo: TodoEntity) =
        viewModelScope.launch(Dispatchers.IO) { todoRepository.addTodo(todo) }

    /** Add Multiple */
    fun addTodos(todos: List<TodoEntity>) =
        viewModelScope.launch(Dispatchers.IO) { todoRepository.addTodos(todos) }

    /** Delete */
    fun deleteTodo(todo: TodoEntity) =
        viewModelScope.launch(Dispatchers.IO) { todoRepository.deleteTodo(todo) }

    /** Delete Multiple */
    fun deleteTodos(todos: List<TodoEntity>) =
        viewModelScope.launch(Dispatchers.IO) { todoRepository.deleteTodos(todos) }

}