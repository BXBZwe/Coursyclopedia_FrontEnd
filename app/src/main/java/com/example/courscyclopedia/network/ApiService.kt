package com.example.courscyclopedia.network

import com.example.courscyclopedia.model.FacultyResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/faculties/getallfaculties")
    fun getAllFaculties(): Call<FacultyResponse>
}

