//package com.example.courscyclopedia.model
//
//import com.google.gson.annotations.SerializedName
//data class Subject(
//    @SerializedName("_id") val id: String?, // You might want to handle the ID as String in Kotlin.
//    @SerializedName("subjectCode") val subjectCode: String,
//    @SerializedName("name") val name: String,
//    // You can use List<String> assuming the IDs are being handled as strings in Kotlin.
//    @SerializedName("professors") val professors: List<String>,
//    @SerializedName("subjectDescription") val subjectDescription: String,
//    @SerializedName("campus") val campus: String,
//    @SerializedName("credit") val credit: Int,
//    @SerializedName("pre_requisite") val preRequisite: List<String>,
//    @SerializedName("co_requisite") val coRequisite: List<String>,
//    @SerializedName("likes") val likes: Int,
//    @SerializedName("subjectStatus") val subjectStatus: String,
//    @SerializedName("last_updated") val lastUpdated: String, // Use a String or a Date type based on your date handling.
//    @SerializedName("available_duration") val availableDuration: Int
//)
