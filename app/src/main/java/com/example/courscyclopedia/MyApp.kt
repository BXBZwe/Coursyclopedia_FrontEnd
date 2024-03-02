package com.example.courscyclopedia

import android.app.Application
import com.example.courscyclopedia.network.ApiService
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.FacultyRepository
import com.example.courscyclopedia.repository.SubjectsRepository

class MyApp : Application() {
    private lateinit var apiService: ApiService
    private lateinit var facultyRepository: FacultyRepository
    private lateinit var subjectRepository: SubjectsRepository

    override fun onCreate() {
        super.onCreate()
        apiService = RetrofitClient.apiService
        facultyRepository = FacultyRepository(apiService)
        subjectRepository = SubjectsRepository(apiService)
    }
}
