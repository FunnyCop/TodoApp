package com.example.todoapp.repo.remote

import com.example.todoapp.model.DeleteResponse
import com.example.todoapp.model.TodoResponse
import com.example.todoapp.model.TokenResponse
import retrofit2.Response
import retrofit2.http.*

interface KnexService {

    @POST("auth/register")
    @Headers("Content-Type: application/json")
    suspend fun register(@Body body: Map<String, String>): Response<TokenResponse>

    @POST("auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body body: Map<String, String>): Response<TokenResponse>

    @GET("todos")
    suspend fun getTodos(@Header("Authorization") token: String): Response<List<TodoResponse>>

    @POST("todos")
    @Headers("Content-Type: application/json")
    suspend fun addTodo(

        @Header("Authorization") token: String,
        @Body body: Map<String, String>

    ): Response<TodoResponse>

    @DELETE("todos/{id}")
    suspend fun deleteTodo(

        @Path("id") id: Int,
        @Header("Authorization") token: String

    ): Response<DeleteResponse>

    @PUT("todos/{id}")
    @Headers("Content-Type: application/json")
    suspend fun updateTodo(

        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body body: Map<String, String>

    ): Response<TodoResponse>

}