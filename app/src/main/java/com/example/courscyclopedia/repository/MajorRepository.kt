//package com.example.courscyclopedia.repository
//
//import com.example.courscyclopedia.model.Major
//import com.example.courscyclopedia.model.Subject
//import com.example.courscyclopedia.network.ApiService
//
//class MajorRepository(private val apiService: ApiService) {
//    suspend fun getEachMajor(majorId: String): Major? {
//        return try {
//            val response = apiService.getEachMajor(majorId)
//            if (response.isSuccessful) response.body() else null
//        } catch (e: Exception) {
//            // Handle exceptions (e.g., logging)
//            null
//        }
//    }
//
//    suspend fun getMajorsForFaculty(facultyId: String): List<Major> {
//        // The try-catch block can handle any exceptions thrown by the API call
//        return try {
//            val response = apiService.getMajorsByFaculty(facultyId)
//            if (response.isSuccessful) {
//                response.body() ?: emptyList()
//            } else {
//                emptyList()
//            }
//        } catch (e: Exception) {
//            // Log the exception and return an empty list or rethrow the exception after logging
//            emptyList()
//        }
//    }
//
//    suspend fun getSubjectsByMajor(majorId: String): List<Subject> {
//        return try {
//            val response = apiService.getSubjectsByMajor(majorId)
//            if (response.isSuccessful) {
//                response.body() ?: emptyList()
//            } else {
//                emptyList()
//            }
//        } catch (e: Exception) {
//            // Log the exception and return an empty list or rethrow the exception after logging
//            emptyList()
//        }
//    }
//
//    // You can add more functions here to fetch subjects by major, etc., following a similar pattern.
//}
