package com.example.courscyclopedia.model
//
//import com.google.gson.annotations.SerializedName
//
//data class User(
//    @SerializedName("_id") val id: String,
//    @SerializedName("email") val email: String,
//    @SerializedName("roles") val roles: List<String>,
//    @SerializedName("wishlists") val wishlists: List<String>,
//    @SerializedName("phoneNumber") val phonenumber: String,
//    @SerializedName("profile") val profile: UserProfile,
//    @SerializedName("facultyId") val facultyId: String?
//)
//data class UserProfile(
//    @SerializedName("firstName") val firstName: String,
//    @SerializedName("lastName") val lastName: String
//)

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("ID") val id: String? = null,
    @SerializedName("Email") val email: String,
    @SerializedName("Roles") val roles: List<String>? = null,
    @SerializedName("Wishlists") val wishlists: List<String>? = null,
    @SerializedName("PhoneNumber") val phonenumber: String? = null,
    @SerializedName("Profile") val profile: UserProfile,
    @SerializedName("FacultyID") val facultyId: String? = null
)

data class UserProfile(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String
)

