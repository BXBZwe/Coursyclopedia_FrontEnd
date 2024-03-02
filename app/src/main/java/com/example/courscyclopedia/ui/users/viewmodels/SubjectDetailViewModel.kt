package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.model.SubjectDetailResponse
import com.example.courscyclopedia.repository.SubjectsRepository
import kotlinx.coroutines.launch

class SubjectDetailViewModel(private val repository: SubjectsRepository) : ViewModel() {
    private val _subjectDetails = MutableLiveData<Subject?>()
    val subjectDetails: MutableLiveData<Subject?> = _subjectDetails
    private val _updateLikesResult = MutableLiveData<SubjectDetailResponse?>()
    val updateLikesResult: LiveData<SubjectDetailResponse?> = _updateLikesResult

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

    fun updateLikes(subjectId: String) {
        viewModelScope.launch {
            try {
                // Get the current subject details to find out the current number of likes
                val currentSubject = repository.getSubjectById(subjectId)
                currentSubject?.let { subject ->
                    // Increment the likes count
                    val updatedLikes = subject.likes + 1

                    // Repository call to update likes, passing in the subjectId and the new likes count
                    val updatedSubjectResponse = repository.updateLikesForSubject(subjectId, updatedLikes)
                    // Update LiveData with the result
                    _updateLikesResult.value = updatedSubjectResponse
                }
            } catch (e: Exception) {
                // If there's an exception, handle it by posting a null or a specific error object to the LiveData
                _updateLikesResult.value = null
            }
        }
    }

}

