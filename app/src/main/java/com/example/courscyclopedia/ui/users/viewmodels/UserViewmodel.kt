package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.UserData
import com.example.courscyclopedia.model.UserList
import com.example.courscyclopedia.repository.UserRepository
import com.example.courscyclopedia.ui.util.Result
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _users = MutableLiveData<List<UserList>>()
    val users: LiveData<List<UserList>> = _users

    private val _user = MutableLiveData<UserData?>()
    val user: LiveData<UserData?> = _user

    private val _isUserInfoComplete = MutableLiveData<Boolean>()
    val isUserInfoComplete: LiveData<Boolean> = _isUserInfoComplete

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = userRepository.getAllUsers()
                if (response.isSuccessful) {
                    // Use Elvis operator ?: to provide an empty list in case body()?.data is null
                    _users.postValue(response.body()?.data ?: emptyList())
                } else {
                    _message.postValue("Failed to fetch data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _message.postValue(e.message ?: "An error occurred")
            }
        }
    }

    fun createUser(user: UserData) {
        viewModelScope.launch {
            when (val result = userRepository.createUser(user)) {
                is Result.Success -> {
                    _user.postValue(result.data.data)
                    _message.postValue("User created successfully")
                }
                is Result.Error -> _message.postValue(result.exception.message ?: "Failed to create user")
                else -> {}
            }

        }
    }

    fun fetchUserByEmail(email: String) {
        viewModelScope.launch {
            when (val result = userRepository.fetchUserByEmail(email)) {
                is Result.Success -> _user.postValue(result.data.data)
                is Result.Error -> _message.postValue(result.exception.message ?: "Error fetching user by email")
                else -> {}
            }
        }
    }
    fun updateUserById(userId: String, user: UserData) {
        viewModelScope.launch {
            when (val result = userRepository.updateUserById(userId, user)) {
                is Result.Success -> {
                    _user.postValue(result.data.data) // Assuming the updated user is returned in the response
                    _message.postValue("User updated successfully")
                }
                is Result.Error -> _message.postValue(result.exception.message ?: "Failed to update user")
                else -> {}
            }
        }
    }

    fun checkUserInfoComplete(userId: String) {
        viewModelScope.launch {
            val isComplete = userRepository.isUserInfoComplete(userId)
            _isUserInfoComplete.postValue(isComplete)
        }
    }
}

//package com.example.courscyclopedia.ui.users.viewmodels
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.courscyclopedia.model.UserData
//import com.example.courscyclopedia.model.UserList
//import com.example.courscyclopedia.repository.UserRepository
//import com.example.courscyclopedia.ui.util.Result
//import kotlinx.coroutines.launch
//import android.content.Context
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//
//class UserViewModel(application: Application,private val userRepository: UserRepository) : AndroidViewModel(application) {
//    private val _users = MutableLiveData<List<UserList>>()
//    val users: LiveData<List<UserList>> = _users
//
//    private val _user = MutableLiveData<UserData?>()
//    val user: LiveData<UserData?> = _user
//
//    private val _isUserInfoComplete = MutableLiveData<Boolean>()
//    val isUserInfoComplete: LiveData<Boolean> = _isUserInfoComplete
//
//    private val _message = MutableLiveData<String>()
//    val message: LiveData<String> = _message
//
//    init {
//        fetchUsers()
//    }
//
//    private fun fetchUsers() {
//        viewModelScope.launch {
//            try {
//                val response = userRepository.getAllUsers()
//                if (response.isSuccessful) {
//                    // Use Elvis operator ?: to provide an empty list in case body()?.data is null
//                    _users.postValue(response.body()?.data ?: emptyList())
//                } else {
//                    _message.postValue("Failed to fetch data: ${response.errorBody()?.string()}")
//                }
//            } catch (e: Exception) {
//                _message.postValue(e.message ?: "An error occurred")
//            }
//        }
//    }
//
//    fun createUser(user: UserData) {
//        viewModelScope.launch {
//            when (val result = userRepository.createUser(user)) {
//                is Result.Success -> {
//                    _user.postValue(result.data.data)
//                    _message.postValue("User created successfully")
//                }
//                is Result.Error -> _message.postValue(result.exception.message ?: "Failed to create user")
//                else -> {}
//            }
//
//        }
//    }
//
//    fun fetchUserByEmail(email: String) {
//        viewModelScope.launch {
//            when (val result = userRepository.fetchUserByEmail(email)) {
//                is Result.Success -> _user.postValue(result.data.data)
//                is Result.Error -> _message.postValue(result.exception.message ?: "Error fetching user by email")
//                else -> {}
//            }
//        }
//    }
//
//    fun updateUserById(userId: String, user: UserData) {
//        viewModelScope.launch {
//            when (val result = userRepository.updateUserById(userId, user)) {
//                is Result.Success -> {
//                    _user.postValue(result.data.data) // Assuming the updated user is returned in the response
//                    _message.postValue("User updated successfully")
//                }
//                is Result.Error -> _message.postValue(result.exception.message ?: "Failed to update user")
//                else -> {}
//            }
//        }
//    }
//
//
//
//
//    fun checkUserInfoComplete(userId: String) {
//        viewModelScope.launch {
//            val isComplete = userRepository.isUserInfoComplete(userId)
//            _isUserInfoComplete.postValue(isComplete)
//        }
//    }
//}


