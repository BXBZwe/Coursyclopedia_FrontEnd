package com.example.courscyclopedia.model

data class CreateSubjectResponse(
    val id: String,
    val message: String?
)

data class NewSubjectRequest(
    val majorId: String,
    val subjectCode: String,
    val name: String,
    val subjectDescription: String,
    val campus: String,
    val credit: Int,
)

