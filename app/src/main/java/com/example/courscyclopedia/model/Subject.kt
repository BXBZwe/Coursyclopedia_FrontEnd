package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName
data class Subject(
    @SerializedName("ID") val id: String,
    @SerializedName("SubjectCode") val subjectCode: String,
    @SerializedName("Name") val subjectname: String,
    @SerializedName("Professors") val professors: List<String>,
    @SerializedName("SubjectDescription") val subjectDescription: String,
    @SerializedName("Campus") val campus: String,
    @SerializedName("Credit") val credit: Int,
    @SerializedName("PreRequisite") val preRequisite: List<String>,
    @SerializedName("CoRequisite") val coRequisite: List<String>,
    @SerializedName("Likes") val likes: Int,
    @SerializedName("Likelist") val likelist: List<String>,
    @SerializedName("SubjectStatus") val subjectStatus: String,
    @SerializedName("LastUpdated") val lastUpdated: String,
    @SerializedName("AvailableDuration") val availableDuration: Int
)
