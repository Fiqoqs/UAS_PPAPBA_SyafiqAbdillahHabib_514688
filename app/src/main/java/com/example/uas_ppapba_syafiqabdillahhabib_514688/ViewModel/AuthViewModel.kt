package com.example.uas_ppapba_syafiqabdillahhabib_514688.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.User
import com.example.uas_ppapba_syafiqabdillahhabib_514688.network.AppDatabase

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    suspend fun login(username: String, password: String) {
        try {
            _authState.value = AuthState.Loading
            val user = userDao.getUser(username, password)
            if (user != null) {
                _authState.value = AuthState.Success(user)
            } else {
                _authState.value = AuthState.Error("Invalid credentials")
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Login failed")
        }
    }

    suspend fun register(username: String, password: String, role: String) {
        try {
            _authState.value = AuthState.Loading

            // Check if username already exists
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                _authState.value = AuthState.Error("Username already exists")
                return
            }

            // Create new user
            val newUser = User(
                username = username,
                password = password,
                role = role
            )
            userDao.insertUser(newUser)
            _authState.value = AuthState.Success(newUser)
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Registration failed")
        }
    }

    sealed class AuthState {
        object Loading : AuthState()
        data class Success(val user: User?) : AuthState()
        data class Error(val message: String) : AuthState()
    }
}