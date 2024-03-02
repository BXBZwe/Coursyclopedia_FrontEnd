package com.example.courscyclopedia.ui.users.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.model.SubjectUpdateRequest
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.repository.UserRepository
import com.example.courscyclopedia.ui.util.Result
import kotlinx.coroutines.launch

class SubjectDetailViewModel(
    private val subjectsRepository: SubjectsRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val subjectDetails = MutableLiveData<Subject?>()
    private val updateStatus = MutableLiveData<Result<String>>()


    fun fetchSubjectDetails(subjectId: String) {
        viewModelScope.launch {
            try {
                val subject = subjectsRepository.getSubjectById(subjectId)
                subjectDetails.value = subject
            } catch (e: Exception) {
                Log.e("SubjectDetailVM", "Error fetching subject details: ${e.message}")
                subjectDetails.value = null
            }
        }
    }

    fun addToWishlist(subjectId: String, userEmail: String) {
        viewModelScope.launch {
            try {
                val userResult = userRepository.fetchUserDetailsByEmail(userEmail)
                Log.d("SubjectDetailVM", "User Result by email: $userResult")
                if (userResult is Result.Success) {
                    val currentUser = userResult.data
                    val updatedWishlist = currentUser.wishlists?.toMutableList() ?: mutableListOf()
                    if (!updatedWishlist.contains(subjectId)) {
                        updatedWishlist.add(subjectId)
                        val updatedUser = currentUser.copy(wishlists = updatedWishlist)
                        userRepository.updateUserById2(currentUser.id, updatedUser)
                        Log.d("SubjectDetailVM", "Wishlist updated: $updatedWishlist")
                    }
                } else {
                    Log.e("SubjectDetailVM", "Failed to fetch user by email: $userEmail")
                }
            } catch (e: Exception) {
                Log.e("SubjectDetailVM", "Error in addToWishlist: ${e.message}")
            }
        }
    }

    fun addLikeToSubject(subjectId: String, userEmail: String) {
        viewModelScope.launch {
            try {
                val response = subjectsRepository.addLikeToSubject(subjectId, userEmail)
                if (response.isSuccessful) {
                    Log.d("SubjectDetailVM", "Like added to subject: $subjectId by user: $userEmail")
                    // Optionally, refresh subject details to show updated likes
                    fetchSubjectDetails(subjectId)
                } else {
                    Log.e("SubjectDetailVM", "Failed to add like to subject: $subjectId, Response: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("SubjectDetailVM", "Error in addLikeToSubject: ${e.message}")
            }
        }
    }

    fun updateSubject(subjectId: String, updateRequest: SubjectUpdateRequest) {
        viewModelScope.launch {
            val result = subjectsRepository.updateSubject(subjectId, updateRequest)
            updateStatus.postValue(result)
        }
    }


}
