package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Major
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.repository.SubjectsRepository
import kotlinx.coroutines.launch

class SubjectsViewModel(private val repository: SubjectsRepository) : ViewModel() {

    private val _majors = MutableLiveData<List<Major>>()
    val majors: LiveData<List<Major>> = _majors

    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    // Example function to fetch majors for a faculty
    fun fetchMajorsForFaculty(facultyId: String) {
        viewModelScope.launch {
            // Repository function to get majors by facultyId
            // This is a suspend function within your repository
            val majorsList = repository.getMajorsForFaculty(facultyId)
            _majors.value = majorsList
        }
    }

    // Example function to fetch subjects for a major
    fun fetchSubjectsForMajor(majorId: String) {
        viewModelScope.launch {
            // Repository function to get subjects by majorId
            // This is a suspend function within your repository
            val subjectsList = repository.getSubjectsForMajor(majorId)
            _subjects.value = subjectsList
        }
    }
}
