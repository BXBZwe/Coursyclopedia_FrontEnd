package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Faculty
import com.example.courscyclopedia.repository.FacultyRepository
import kotlinx.coroutines.launch

class FacultyViewModel(private val facultyRepository: FacultyRepository) : ViewModel() {
    private val _faculties = MutableLiveData<List<Faculty>>()
    val faculties: LiveData<List<Faculty>> = _faculties

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        fetchFaculties()
    }

    private fun fetchFaculties() {
        viewModelScope.launch {
            try {
                val response = facultyRepository.getAllFaculties()
                if (response != null && response.isSuccessful) {
                    _faculties.postValue(response.body()?.data ?: emptyList())
                } else {
                    _message.postValue("Failed to fetch data")
                }
            } catch (e: Exception) {
                _message.postValue(e.message ?: "An error occurred")
            }
        }
    }
}


