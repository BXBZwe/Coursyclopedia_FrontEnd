package com.example.courscyclopedia.repository

import com.example.courscyclopedia.model.FacultyResponse
import com.example.courscyclopedia.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FacultyRepository {
    fun getAllFaculties(callback: (FacultyResponse?) -> Unit) {
        RetrofitClient.instance.getAllFaculties().enqueue(object : Callback<FacultyResponse> {
            override fun onResponse(call: Call<FacultyResponse>, response: Response<FacultyResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<FacultyResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}

