package com.example.courscyclopedia.network

import com.example.courscyclopedia.model.FacultyResponse
import com.example.courscyclopedia.model.MajorResponse
import com.example.courscyclopedia.model.SubjectResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/faculties/getallfaculties")
    suspend fun getAllFaculties(): Response<FacultyResponse>
//
//    @GET("api/faculties/geteachfaculty/{id}")
//    suspend fun getEachFaculty(@Path("id") facultyId: String): Response<Faculty>

//    @GET("api/majors/geteachmajor/{id}")
//    suspend fun getEachMajor(@Path("id") majorId: String): Response<MajorResponse>

    @GET("api/faculties/getamjorforfaculty/{id}")
    suspend fun getMajorsForFaculty(@Path("id") facultyId: String): Response<MajorResponse>

    @GET("api/majors/getsubjectsforeachmajor/{id}")
    suspend fun getSubjectsForMajor(@Path("id") majorId: String): Response<SubjectResponse>

}


