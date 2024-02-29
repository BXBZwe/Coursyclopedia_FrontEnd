package com.example.courscyclopedia.repository

import com.example.courscyclopedia.model.FacultyResponse
import com.example.courscyclopedia.model.User
import com.example.courscyclopedia.model.UserResponse
import com.example.courscyclopedia.network.ApiService
import com.example.courscyclopedia.ui.util.Result

class UserRepository(private val apiService: ApiService) {

    suspend fun getAllFaculties(): Result<FacultyResponse> {
        return try {
            val response = apiService.getAllFaculties()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(RuntimeException("response error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    // UserRepository.kt
    suspend fun createUser(user: User): Result<UserResponse> {
        return try {
            val response = apiService.createUser(user)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("API call successful but empty response body"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // UserRepository.kt
    suspend fun fetchUserByEmail(email: String): Result<UserResponse> {
        return try {
            val response = apiService.getUserbyEmail(email)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                // You can further refine this to handle different HTTP errors differently
                Result.Error(Exception("Error fetching user by email: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    suspend fun isUserInfoComplete(userId: String): Boolean {
        return try {
            val response = apiService.getUserById(userId)
            if (response.isSuccessful && response.body() != null) {
                val userData = response.body()?.data
                // Check if phoneNumber, firstName, lastName, and facultyId are not null and not empty
                userData?.let {
                    it.phonenumber.isNotBlank() &&
                            it.profile.firstName.isNotBlank() &&
                            it.profile.lastName.isNotBlank() &&
                            it.facultyId.isNotBlank()
                } ?: false
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }


}
