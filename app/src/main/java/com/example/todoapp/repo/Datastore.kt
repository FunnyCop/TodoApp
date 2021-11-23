package com.example.todoapp.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

/** Preferences Datastore repository */
object Datastore {

    /** Preferences Datastore name for login/registration */
    private const val DATASTORE_KEY = "login_info"

    /** Preferences Datastore name for username */
    private const val USERNAME_KEY = "username"

    /** Preferences Datastore name for password */
    private const val PASSWORD_KEY = "password"

    /** Preferences Datastore name for token */
    private const val TOKEN_KEY = "token"

    /** Getter for string preferences key */
    private val String.preferencesKey get() = stringPreferencesKey(this)

    /** Preferences Datastore for login/registration */
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_KEY)

    /** Getter for username from preferences datastore */
    val Context.username get() = this.datastore.data.map { it[USERNAME_KEY.preferencesKey] }

    /** Getter for password from preferences datastore */
    val Context.password get() = this.datastore.data.map { it[PASSWORD_KEY.preferencesKey] }

    /** Getter for token from preferences datastore */
    val Context.token get() = this.datastore.data.map { it[TOKEN_KEY.preferencesKey] }

    /** Setter for username from preferences datastore */
    suspend fun Context.setUsername(username: String) =
        this.datastore.edit { it[USERNAME_KEY.preferencesKey] = username }

    /** Setter for password from preferences datastore */
    suspend fun Context.setPassword(password: String) =
        this.datastore.edit { it[PASSWORD_KEY.preferencesKey] = password }

    /** Setter for token from preferences datastore */
    suspend fun Context.setToken(token: String) =
        this.datastore.edit { it[TOKEN_KEY.preferencesKey] = token }

}