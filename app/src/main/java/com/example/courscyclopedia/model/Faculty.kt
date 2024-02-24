package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName

data class Faculty(
    @SerializedName("_id") val id: String, // ObjectIDs will be handled as Strings in Kotlin
    @SerializedName("facultyName") val facultyName: String,
    @SerializedName("majorIDs") val majorIDs: List<String> // List of major IDs as Strings
)