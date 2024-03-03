package com.example.courscyclopedia.ui.users.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.repository.UserRepository
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val userRepository: UserRepository,
    private val subjectsRepository: SubjectsRepository
) : ViewModel() {

    private val _wishlistSubjects = MutableLiveData<List<Subject>>()
    val wishlistSubjects: LiveData<List<Subject>> = _wishlistSubjects

    fun fetchWishlistSubjects(userEmail: String) {
        viewModelScope.launch {
            try {
                val userDetails = userRepository.fetchUserDetailsByEmail(userEmail)
                val wishlistSubjectsIds = userDetails // Assuming 'wishlists' is the field containing wishlist subject IDs

//                val wishlistSubjects = mutableListOf<Subject>()
//                wishlistSubjectsIds.forEach { subjectId ->
//                    subjectsRepository.getSubjectById(subjectId)?.let {
//                        wishlistSubjects.add(it)
//                    }
//                }

//                _wishlistSubjects.value = wishlistSubjects
            } catch (e: Exception) {
                // Handle errors appropriately
                Log.e("com.example.courscyclopedia.ui.users.viewmodels.WishlistViewModel", "Error fetching wishlist subjects: ${e.message}")
            }
        }
    }

}
