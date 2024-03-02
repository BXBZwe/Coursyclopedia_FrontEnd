    package com.example.courscyclopedia.repository

    import android.util.Log
    import com.example.courscyclopedia.model.UserData
    import com.example.courscyclopedia.model.UserDetails
    import com.example.courscyclopedia.model.UserList
    import com.example.courscyclopedia.model.UserResponse
    import com.example.courscyclopedia.network.ApiService
    import com.example.courscyclopedia.ui.util.Result
    import retrofit2.Response

    class UserRepository(private val apiService: ApiService) {

        suspend fun getAllUsers(): Response<UserList> {
            return apiService.getAllUsers()
        }

        suspend fun createUser(user: UserData): Result<UserResponse> {
            return try {
                val response = apiService.createUser(user)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception("Failed to create user: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        suspend fun fetchUserByEmail(email: String): Result<UserResponse> {
            return try {
                val response = apiService.getUserbyEmail(email)
//                Log.d("APIResponse", "Response: ${response.raw().body?.string()}")
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)

                } else {
                    Result.Error(Exception("Failed to fetch user by email: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        suspend fun fetchUserDetailsByEmail(email: String): Result<UserDetails> {
            return try {
                val response = apiService.getUserByEmail2(email)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception("Failed to fetch user by email: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        suspend fun fetchUserByEmail2(email: String): Result<UserResponse> {
            return try {
                val response = apiService.getUserbyEmail(email)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Error(Exception("Response body is null"))
                    }
                } else {
                    Result.Error(Exception("Failed to fetch user by email: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


        suspend fun getUserById(userId: String): Result<UserResponse> {
            return try {
                val response = apiService.getUserById(userId)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception("Failed to fetch user by ID: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        suspend fun updateUserById(userId: String, user: UserData): Result<UserResponse> {
            return try {
                val response = apiService.updateUserById(userId, user)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception("Failed to update user: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        suspend fun updateUserById2(userId: String, user: UserDetails): Result<UserResponse> {
            return try {
                val response = apiService.updateUserById2(userId, user)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception("Failed to update user: ${response.code()} ${response.message()}"))
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
                    userData?.let {
                        return (it.phoneNumber?.isNotBlank() ?: false) &&
                                (it.profile.firstName?.isNotBlank() ?: false) &&
                                (it.profile.lastName?.isNotBlank() ?: false) &&
                                (it.facultyId?.isNotBlank() ?: false)
                    } ?: false
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        }

        suspend fun checkIfUserLikedSubject(subjectId: String, userEmail: String): Boolean {
            return try {
                // Placeholder for the actual API call, replace with your actual implementation
                // val response = apiService.checkUserLikedSubject(subjectId, userEmail)
                // Assume the API returns a boolean directly or a response object from which you can get the boolean
                val hasLiked = false // This should be replaced with the actual result from your API call

                hasLiked
            } catch (e: Exception) {
                Log.e("UserRepository", "Error checking if user liked subject: ${e.message}")
                false // Assume the user hasn't liked the subject in case of an error
            }
        }


    }
