package com.example.courscyclopedia.model

data class UserResponse(
    val success: Boolean,
    val data: UserData,
    val message: String
)

data class UserData(
    val id: String,
    val email: String,
    val roles: List<String>?, // Assuming roles is a list of strings, change it accordingly if different
    val wishlists: List<String>?, // Assuming wishlists is a list of strings, change it accordingly if different
    val phonenumber: String,
    val profile: UserProfile,
    val facultyId: String
)


