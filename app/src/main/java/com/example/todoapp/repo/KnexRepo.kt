package com.example.todoapp.repo

import android.util.ArrayMap
import com.example.todoapp.model.TodoResponse
import com.example.todoapp.repo.remote.RetrofitInstance
import com.example.todoapp.model.TokenResponse

/** API Repository */
object KnexRepo {

    /** API Service */
    private val knexService by lazy { RetrofitInstance.knexService }

    /** Register */
    suspend fun register(username: String, email: String, password: String): TokenResponse? {

        val requestBody = ArrayMap<String, String>()

        requestBody["username"] = username
        requestBody["email"] = email
        requestBody["password"] = password

        val response = knexService.register(requestBody)

        return if(response.isSuccessful) response.body() else null

    }

    /** Login */
    suspend fun login(username: String, password: String): TokenResponse? {

        val requestBody = ArrayMap<String, String>()

        requestBody["username"] = username
        requestBody["password"] = password

        val response = knexService.login(requestBody)

        return if (response.isSuccessful) response.body() else null

    }

    /** Get all */
    suspend fun getTodos(token: String): List<TodoResponse>? {

        val response = knexService.getTodos(token)

        return if (response.isSuccessful) response.body() else null

    }

    /** Add */
    suspend fun addTodo(title: String, description: String, token: String): TodoResponse? {

        val requestBody = ArrayMap<String, String>()

        requestBody["title"] = title
        requestBody["description"] = description

        val response = knexService.addTodo(token, requestBody)

        return if (response.isSuccessful) response.body() else null

    }

    /** Delete */
    suspend fun deleteTodo(id: Int, token: String) = knexService.deleteTodo(id, token).isSuccessful

    /** Update */
    suspend fun updateTodo(id: Int, title: String, description: String, token: String): TodoResponse? {

        val requestBody = ArrayMap<String, String>()

        requestBody["title"] = title
        requestBody["description"] = description

        val response = knexService.updateTodo(id, token, requestBody)

        return if (response.isSuccessful) response.body() else null

    }

}