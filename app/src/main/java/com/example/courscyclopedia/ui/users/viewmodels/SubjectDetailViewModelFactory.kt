package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.repository.UserRepository

class SubjectDetailViewModelFactory(
    private val subjectsRepository: SubjectsRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubjectDetailViewModel(subjectsRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//package com.example.courscyclopedia.ui.users.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.courscyclopedia.repository.SubjectsRepository
//
//class SubjectDetailViewModelFactory(private val repository: SubjectsRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SubjectDetailViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SubjectDetailViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
