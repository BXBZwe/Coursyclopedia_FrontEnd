package com.example.courscyclopedia.ui.users.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.model.SubjectDetailResponse
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.repository.UserRepository
import com.example.courscyclopedia.ui.util.Result
import kotlinx.coroutines.launch

class SubjectDetailViewModel(
    private val subjectsRepository: SubjectsRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val subjectDetails = MutableLiveData<Subject?>()

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


}


//package com.example.courscyclopedia.ui.users.viewmodels
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.courscyclopedia.model.Subject
//import com.example.courscyclopedia.repository.SubjectsRepository
//import com.example.courscyclopedia.repository.UserRepository
//import com.example.courscyclopedia.ui.util.Result
//import kotlinx.coroutines.launch
//
//class SubjectDetailViewModel(
//    private val subjectsRepository: SubjectsRepository,
//    private val userRepository: UserRepository
//) : ViewModel() {
//    val subjectDetails = MutableLiveData<Subject?>()
//
//    fun fetchSubjectDetails(subjectId: String) {
//        viewModelScope.launch {
//            try {
//                val subject = subjectsRepository.getSubjectById(subjectId)
//                subjectDetails.value = subject
//            } catch (e: Exception) {
//                // Handle errors
//                subjectDetails.value = null
//            }
//        }
//    }
//    fun addToWishlist(subjectId: String, userEmail: String) {
//        Log.d("addToWishlist", "Subject $userEmail" )
//        viewModelScope.launch {
//            try {
//                // Attempt to fetch the user by email
//                val userResult = userRepository.fetchUserByEmail(userEmail)
//                Log.d("addToWishlist", "Result $userResult" )
//                if (userResult is Result.Success) {
//                    val currentUser = userResult.data.data
//
//                    // Check if currentUser is not null
//                    if (currentUser != null) {
//                        // Prepare the updated wishlist
//                        val updatedWishlist = currentUser.wishlists?.toMutableList() ?: mutableListOf()
//                        if (!updatedWishlist.contains(subjectId)) {
//                            updatedWishlist.add(subjectId)
//
//                            // Update the user object with the new wishlist
//                            val updatedUser = currentUser.copy(wishlists = updatedWishlist)
//
//                            // Attempt to update the user in the repository
//                            val updateResult = userRepository.updateUserById(currentUser.id!!, updatedUser)
//                            if (updateResult is Result.Success) {
//                                Log.d("addToWishlist", "Wishlist updated successfully")
//                            } else {
//                                Log.d("addToWishlist", "Failed to update wishlist: ${(updateResult as Result.Error).exception.message}")
//                            }
//                        }
//                    } else {
//                        Log.d("addToWishlist", "User not found")
//                    }
//                } else {
//                    Log.d("addToWishlist", "Failed to fetch user by email: ${(userResult as Result.Error).exception.message}")
//                }
//            } catch (e: Exception) {
//                Log.d("addToWishlist", "Error in addToWishlist: ${e.message}")
//            }
//        }
//    }
//}
//
//

//package com.example.courscyclopedia.ui.users.viewmodels
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.courscyclopedia.model.Subject
//import com.example.courscyclopedia.repository.SubjectsRepository
//import com.example.courscyclopedia.repository.UserRepository
//import kotlinx.coroutines.launch
//
//class SubjectDetailViewModel(private val repository: SubjectsRepository) : ViewModel() {
//    private val _subjectDetails = MutableLiveData<Subject?>()
//    val subjectDetails: MutableLiveData<Subject?> = _subjectDetails
//
//    fun getLoggedInUserEmail(context: Context): String? {
//        val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
//        return sharedPreferences.getString("userEmail", null)
//    }
//
//    fun addToWishlist(context: Context, subjectId: String) {
//        viewModelScope.launch {
//            val email = getLoggedInUserEmail(context)
//            email?.let {
//                val userResult = userRepository.fetchUserByEmail(email)
//                if (userResult is Result.Success) {
//                    val currentUser = userResult.data
//                    val updatedWishlist = currentUser.wishlists?.toMutableList() ?: mutableListOf()
//                    if (!updatedWishlist.contains(subjectId)) {
//                        updatedWishlist.add(subjectId)
//                        val updatedUser = currentUser.copy(wishlists = updatedWishlist)
//                        userRepository.updateUserById(currentUser.id, updatedUser)
//                    }
//                } else {
//                    // Handle error
//                }
//            }
//        }
//    }
//
//    fun fetchSubjectDetails(subjectId: String) {
//        viewModelScope.launch {
//            try {
//                val subject = repository.getSubjectById(subjectId)
//                _subjectDetails.value = subject
//            } catch (e: Exception) {
//                // Handle errors or set a value indicating an error to _subjectDetails
//            }
//        }
//    }
//}
//
