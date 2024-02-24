package com.example.courscyclopedia.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courscyclopedia.repository.FacultyRepository


class FacultyViewModelFactory(private val facultyRepository: FacultyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FacultyViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FacultyViewmodel(facultyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}