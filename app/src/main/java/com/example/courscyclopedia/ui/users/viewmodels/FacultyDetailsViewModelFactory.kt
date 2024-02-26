//package com.example.courscyclopedia.ui.users.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.courscyclopedia.repository.MajorRepository
//
//class FacultyDetailsViewModelFactory(private val majorRepository: MajorRepository) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(FacultyDetailsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return FacultyDetailsViewModel(majorRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
