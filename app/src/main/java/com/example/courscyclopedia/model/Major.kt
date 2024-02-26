package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName

data class Major(
    @SerializedName("ID") val id: String,
    @SerializedName("MajorName") val majorName: String,
//    @SerializedName("Image") val image: String?,  // Nullable since it can be null
    @SerializedName("SubjectIDs") val subjectIDs: List<String>
)

