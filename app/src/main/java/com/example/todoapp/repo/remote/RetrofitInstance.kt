package com.example.todoapp.repo.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://knex-todo.herokuapp.com/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val knexService = retrofit.create<KnexService>()

}