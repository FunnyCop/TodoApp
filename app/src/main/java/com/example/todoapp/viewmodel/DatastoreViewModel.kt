package com.example.todoapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.repo.Datastore.password
import com.example.todoapp.repo.Datastore.setPassword
import com.example.todoapp.repo.Datastore.setToken
import com.example.todoapp.repo.Datastore.setUsername
import com.example.todoapp.repo.Datastore.token
import com.example.todoapp.repo.Datastore.username
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** Preferences Datastore View Model */
class DatastoreViewModel : ViewModel() {

    /** Getter for username from preferences datastore */
    fun username(context: Context?) =
        context?.username?.asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    /** Getter for password from preferences datastore */
    fun password(context: Context?) =
        context?.password?.asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    /** Getter for token from preferences datastore */
    fun token(context: Context?) =
        context?.token?.asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    /** Setter for username from preferences datastore */
    fun setUsername(context: Context?, username: String) =
        viewModelScope.launch(Dispatchers.IO) { context?.setUsername(username) }

    /** Setter for password from preferences datastore */
    fun setPassword(context: Context?, password: String) =
        viewModelScope.launch(Dispatchers.IO) { context?.setPassword(password) }

    /** Setter for token from preferences datastore */
    fun setToken(context: Context?, token: String) =
        viewModelScope.launch(Dispatchers.IO) { context?.setToken(token) }

}