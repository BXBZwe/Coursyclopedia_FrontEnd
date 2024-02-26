package com.example.courscyclopedia.model

data class SubjectDetailResponse(
    val data: Subject, // Assuming the JSON has a "data" field containing the subject details
    val message: String?
)
