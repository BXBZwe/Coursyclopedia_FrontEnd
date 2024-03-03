package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.repository.UserRepository

class WishlistViewModelFactory(
    private val userRepository: UserRepository,
    private val subjectsRepository: SubjectsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WishlistViewModel(userRepository, subjectsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
