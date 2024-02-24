package com.example.courscyclopedia.repository

import com.example.courscyclopedia.model.Faculty
import com.example.courscyclopedia.network.RetrofitClient
import retrofit2.Call

class FacultyRepository {
    fun getAllFaculties(): Call<List<Faculty>> {
        return RetrofitClient.instance.getAllFaculties()
    }
}