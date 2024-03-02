package com.example.courscyclopedia.ui.professors.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.repository.SubjectsRepository
import kotlinx.coroutines.launch

class ProfessorViewModel(private val subjectsRepository: SubjectsRepository) : ViewModel() {
    // LiveData to hold the subjects
    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _deletedSubjectId = MutableLiveData<String?>()
    val deletedSubjectId: LiveData<String?> = _deletedSubjectId

    init {
        fetchAllSubjects()
    }
    private fun fetchAllSubjects() {
        viewModelScope.launch {
            try {
                val response = subjectsRepository.getAllSubjects()
                if (response != null && response.isSuccessful) {
                    _subjects.postValue(response.body()?.data ?: emptyList())
                } else {
                    _message.postValue("Failed to fetch data")
                }
            } catch (e: Exception) {
                _message.postValue(e.message ?: "An error occurred")
            }
        }
    }
    fun deleteSubject(subjectId: String) {
        viewModelScope.launch {
            try {
                val response = subjectsRepository.deleteSubject(subjectId)
                if (response.isSuccessful) {
                    // Subject deletion was successful, remove the subject from the list
                    val currentSubjects = _subjects.value ?: emptyList() // Use emptyList() if _subjects.value is null
                    val updatedSubjects = currentSubjects.filterNot { it.id == subjectId }
                    _subjects.postValue(updatedSubjects) // updatedSubjects is now guaranteed to be non-nullable
                    _message.postValue("Subject successfully deleted")
                } else {
                    _message.postValue("Failed to delete subject: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _message.postValue(e.message ?: "An error occurred while deleting the subject")
            }
        }
    }

}
