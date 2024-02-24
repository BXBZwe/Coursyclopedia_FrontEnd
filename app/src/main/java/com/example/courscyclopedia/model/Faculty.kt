package com.example.courscyclopedia.model

import com.google.gson.annotations.SerializedName

data class Faculty(
    @SerializedName("ID") val id: String, // ObjectIDs will be handled as Strings in Kotlin
    @SerializedName("FacultyName") val facultyName: String,
    @SerializedName("MajorIDs") val majorIDs: List<String> // List of major IDs as Strings
)