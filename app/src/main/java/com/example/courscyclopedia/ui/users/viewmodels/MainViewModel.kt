package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _isUserInfoComplete = MutableLiveData<Boolean>()
    val isUserInfoComplete: LiveData<Boolean> = _isUserInfoComplete

    fun checkUserInfoComplete(userId: String) {
        viewModelScope.launch {
            // Assuming userRepository has a method to check if user info is complete
            val isComplete = userRepository.isUserInfoComplete(userId)
            _isUserInfoComplete.value = isComplete
        }
    }
}
