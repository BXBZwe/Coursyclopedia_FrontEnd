package com.example.courscyclopedia.repository

import com.example.courscyclopedia.model.CreateSubjectResponse
import com.example.courscyclopedia.model.Major
import com.example.courscyclopedia.model.NewSubjectRequest
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.model.SubjectDetailResponse
import com.example.courscyclopedia.model.SubjectResponse
import com.example.courscyclopedia.network.ApiService
import retrofit2.Response

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

    suspend fun getAllSubjects(): Response<SubjectResponse> {
        return apiService.getAllSubjects()
    }

    suspend fun createSubject(newSubjectRequest: NewSubjectRequest): CreateSubjectResponse {
        val response = apiService.createSubject(newSubjectRequest)
        if (response.isSuccessful) {
            // If the response is successful, return the response body which is a CreateSubjectResponse object
            return response.body() ?: throw Exception("Response body is null")
        } else {
            // If the response is not successful, throw an exception with the error message
            throw Exception("Error creating subject: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getMajors(): List<Major> {
        val response = apiService.getAllMajors()
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error fetching majors: ${response.errorBody()?.string()}")
        }
    }

    suspend fun updateLikesForSubject(subjectId: String, newLikesCount: Int): SubjectDetailResponse {
        val requestBody = mapOf("likes" to newLikesCount)
        val response = apiService.updateLikes(subjectId, requestBody)
        if (response.isSuccessful) {
            // Return the response body directly
            return response.body() ?: throw Exception("Response body is null")
        } else {
            // Throw an exception with the error body
            throw Exception("Error updating likes: ${response.errorBody()?.string()}")
        }
    }


}

