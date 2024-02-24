package com.example.courscyclopedia.network

import com.example.courscyclopedia.model.Faculty
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/faculties/getallfaculties")
    fun getAllFaculties(): Call<List<Faculty>>
}