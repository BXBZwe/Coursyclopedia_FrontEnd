package com.example.courscyclopedia.ui.professors.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courscyclopedia.repository.SubjectsRepository

class ProfessorViewModelFactory(private val subjectsRepository: SubjectsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfessorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfessorViewModel(subjectsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}