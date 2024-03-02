package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("ID") val id: String? = null,
    @SerializedName("Email") val email: String,
    @SerializedName("Roles") val roles: List<String>? = null,
    @SerializedName("Wishlists") val wishlists: List<String>? = null,
    @SerializedName("PhoneNumber") val phoneNumber: String? = null,
    @SerializedName("Profile") val profile: UserProfile,
    @SerializedName("FacultyID") val facultyId: String? = null
)

data class UserProfile(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String
)


