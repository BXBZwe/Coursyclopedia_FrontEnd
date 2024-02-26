package com.example.courscyclopedia.repository

import com.example.courscyclopedia.model.FacultyResponse
import com.example.courscyclopedia.network.ApiService
import retrofit2.Response

class FacultyRepository(private val apiService: ApiService) {
    suspend fun getAllFaculties(): Response<FacultyResponse> {
        return apiService.getAllFaculties()
    }
}



