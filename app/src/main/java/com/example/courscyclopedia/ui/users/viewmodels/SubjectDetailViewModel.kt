package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.repository.SubjectsRepository
import kotlinx.coroutines.launch

class SubjectDetailViewModel(private val repository: SubjectsRepository) : ViewModel() {
    private val _subjectDetails = MutableLiveData<Subject?>()
    val subjectDetails: MutableLiveData<Subject?> = _subjectDetails

    fun fetchSubjectDetails(subjectId: String) {
        viewModelScope.launch {
            try {
                val subject = repository.getSubjectById(subjectId)
                _subjectDetails.value = subject
            } catch (e: Exception) {
                // Handle errors or set a value indicating an error to _subjectDetails
            }
        }
    }
}

