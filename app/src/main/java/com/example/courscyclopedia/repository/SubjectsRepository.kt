package com.example.courscyclopedia.repository

import com.example.courscyclopedia.model.Major
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.network.ApiService

class SubjectsRepository(private val apiService: ApiService) {

    // Function to fetch majors for a given faculty ID
    suspend fun getMajorsForFaculty(facultyId: String): List<Major> {
        val response = apiService.getMajorsForFaculty(facultyId)
        if (response.isSuccessful) {
            // The response is expected to be a MajorResponse object, which contains a List<Major>
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error fetching majors: ${response.errorBody()?.string()}")
        }
    }

    // Function to fetch subjects for a given major ID
    suspend fun getSubjectsForMajor(majorId: String): List<Subject> {
        val response = apiService.getSubjectsForMajor(majorId)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error fetching subjects: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getSubjectById(subjectId: String): Subject? {
        val response = apiService.getSubjectById(subjectId)
        if (response.isSuccessful) {
            return response.body()?.data
        } else {
            throw Exception("Error fetching subject: ${response.errorBody()?.string()}")
        }
    }

}

