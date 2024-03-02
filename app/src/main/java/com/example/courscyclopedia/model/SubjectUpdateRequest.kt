package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName

data class SubjectUpdateRequest(
    @SerializedName("subjectCode") val subjectCode: String,
    @SerializedName("name") val name: String,
//    @SerializedName("professors") val professors: List<String>, // Assuming you will pass professor IDs as Strings
    @SerializedName("subjectDescription") val subjectDescription: String,
    @SerializedName("campus") val campus: String,
//    @SerializedName("credit") val credit: Int,
//    @SerializedName("preRequisite") val preRequisite: List<String>?,
//    @SerializedName("coRequisite") val coRequisite: List<String>?,
//    @SerializedName("subjectStatus") val subjectStatus: String,
//    @SerializedName("availableDuration") val availableDuration: Int?
)
