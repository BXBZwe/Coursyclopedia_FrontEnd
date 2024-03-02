package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("ID") val id: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Roles") val roles: List<String>,
    @SerializedName("Wishlists") val wishlists: List<String>?,
    @SerializedName("PhoneNumber") val phoneNumber: String,
    @SerializedName("Profile") val profile: UserDetailsProfile,
    @SerializedName("FacultyID") val facultyId: String
)

data class UserDetailsProfile(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String
)
