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
    private val _likeStatus = MutableLiveData<String>()
    val likeStatus: LiveData<String> = _likeStatus

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

    fun addLike(subjectId: String, userEmail: String) {
        viewModelScope.launch {
            try {
                val response = repository.addLikeToSubject(subjectId, userEmail)
                if (response.isSuccessful) {
                    _likeStatus.value = "Liked"
                } else {
                    _likeStatus.value = "Error"
                }
            } catch (e: Exception) {
                _likeStatus.value = e.message
            }
        }
    }


}

