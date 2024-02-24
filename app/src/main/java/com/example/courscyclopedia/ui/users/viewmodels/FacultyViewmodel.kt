package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courscyclopedia.model.Faculty
import com.example.courscyclopedia.repository.FacultyRepository

class FacultyViewmodel(private val facultyRepository: FacultyRepository) : ViewModel() {
    private val _faculties = MutableLiveData<List<Faculty>>()
    val faculties: LiveData<List<Faculty>> = _faculties

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        fetchFaculties()
    }

    private fun fetchFaculties() {
        facultyRepository.getAllFaculties { facultyResponse ->
            facultyResponse?.data?.let {
                _faculties.postValue(it)
            } ?: run {
                _faculties.postValue(emptyList()) // Post an empty list if the response is null
                _message.postValue("Failed to fetch data")
            }
        }
    }
}

