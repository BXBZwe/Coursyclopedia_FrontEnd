package com.example.courscyclopedia

import android.app.Application
import com.example.courscyclopedia.network.ApiService
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.FacultyRepository

class MyApp : Application() {
    lateinit var apiService: ApiService
    lateinit var facultyRepository: FacultyRepository

    override fun onCreate() {
        super.onCreate()
        apiService = RetrofitClient.apiService
        facultyRepository = FacultyRepository(apiService)
    }
}


// Remember to declare it in your AndroidManifest.xml
