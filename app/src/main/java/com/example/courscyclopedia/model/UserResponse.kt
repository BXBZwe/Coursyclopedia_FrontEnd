package com.example.courscyclopedia.model

data class UserResponse(
    val success: Boolean,
    val data: UserData?,
    val message: String
)

data class UserData(
    val id: String? = null,
    val email: String,
    val roles: List<String>? = null,
    val wishlists: List<String>? = null,
    val phonenumber: String? = null,
    val profile: UserProfile,
    val facultyId: String? = null
)

data class UserList(
    val data: List<UserList>,
    val message: String
)
