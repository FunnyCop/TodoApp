package com.example.todoapp.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.todoapp.model.TodoResponse
import com.example.todoapp.repo.Datastore.setPassword
import com.example.todoapp.repo.Datastore.setToken
import com.example.todoapp.repo.Datastore.setUsername
import com.example.todoapp.repo.Datastore.token
import com.example.todoapp.repo.KnexRepo
import com.example.todoapp.repo.local.model.TodoEntity
import com.example.todoapp.util.convert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** API View Model */
class KnexViewModel : ViewModel() {

    private val _todos = MutableLiveData<List<TodoResponse>?>()
    val todos: LiveData<List<TodoResponse>?> get() = _todos

    /** Register */
    fun register(username: String, email: String, password: String, context: Context?) =
        viewModelScope.launch(Dispatchers.IO) {

            /** Register with API Repo */
            val response = KnexRepo.register(username, email, password)

            /** If registration was successful */
            if (response != null) {

                /** Save information in Preferences Datastore */
                context?.setUsername(username)
                context?.setPassword(password)
                context?.setToken(response.token)

            }

        }

    /** Login */
    fun login(username: String, password: String, context: Context?) =
        viewModelScope.launch(Dispatchers.IO) {

            /** Login with API Repo */
            val response = KnexRepo.login(username, password)

            /** If login was successful */
            if (response != null) {

                /** Save information in Preferences Datastore */
                context?.setUsername(username)
                context?.setPassword(password)
                context?.setToken(response.token)

            }

        }

    /** Get all */
    fun getTodos(context: Context?) = viewModelScope.launch(Dispatchers.IO) {

        /** Get Token from Preferences Datastore */
        val token = context?.token?.first()

        /** Get Todos from API Repo */
        val response = token?.let { KnexRepo.getTodos(it) }

        /** If request was successful, save Todos */
        response.let { _todos.postValue(it) }

    }

    /** Add */
    fun addTodo(

        title: String,
        description: String,
        context: Context?,
        todoViewModel: TodoViewModel,
        callback: () -> Unit

    ) = viewModelScope.launch {

        /** Get Token from Preferences Datastore */
        val token = context?.token?.first()

        /** Add with API Repo */
        val response = token?.let { KnexRepo.addTodo(title, description, token) }

        val todos = _todos.value?.toMutableList()

        /** If request was successful and returned Todos */
        if (response != null && todos != null) {

            /** Save Todos */
            _todos.postValue(todos.also { todos.add(response) })

            /** Add to Room DB */
            todoViewModel.addTodo(response.convert())

            /** Execute Callback */
            callback()

        }

    }

    /** Delete */
    fun deleteTodo(todo: TodoEntity, todoViewModel: TodoViewModel, context: Context?) =
        viewModelScope.launch(Dispatchers.IO) {

            /** Get Token from Preferences Datastore */
            val token = context?.token?.first()

            /** Delete Todos with API Repo */
            val response = token?.let { KnexRepo.deleteTodo(todo.id, it) }

            /** If request was successful */
            if (response == true)
                /** Save Todos */
                _todos.postValue(_todos.value?.toMutableList().also {

                    /** Remove selected */
                    it?.remove(todo.convert())

                }).also {

                    /** Delete with Room DB */
                    todoViewModel.deleteTodo(todo)

                }

        }

    /** Update */
    fun updateTodo(

        id: Int,
        title: String,
        description: String,
        context: Context?,
        callback: () -> Unit

    ) = viewModelScope.launch {

        /** Get Token from Preferences Datastore */
        val token = context?.token?.first()

        /** Update with API Repo */
        val response = token?.let { KnexRepo.updateTodo(id, title, description, it) }

        /** If request was successful */
        if (response != null)
            /** Execute callback */
            callback()

    }

}