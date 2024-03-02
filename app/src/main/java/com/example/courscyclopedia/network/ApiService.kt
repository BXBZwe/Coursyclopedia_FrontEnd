package com.example.courscyclopedia.network

import com.example.courscyclopedia.model.CreateSubjectResponse
import com.example.courscyclopedia.model.FacultyResponse
import com.example.courscyclopedia.model.MajorResponse
import com.example.courscyclopedia.model.NewSubjectRequest
import com.example.courscyclopedia.model.SubjectDetailResponse
import com.example.courscyclopedia.model.SubjectResponse
import com.example.courscyclopedia.model.UserData
import com.example.courscyclopedia.model.UserList
import com.example.courscyclopedia.model.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/faculties/getallfaculties")
    suspend fun getAllFaculties(): Response<FacultyResponse>

    @GET("api/majors/getallmajors")
    suspend fun getAllMajors(): Response<MajorResponse>

    @GET("api/faculties/getamjorforfaculty/{id}")
    suspend fun getMajorsForFaculty(@Path("id") facultyId: String): Response<MajorResponse>

    @GET("api/majors/getsubjectsforeachmajor/{id}")
    suspend fun getSubjectsForMajor(@Path("id") majorId: String): Response<SubjectResponse>

    @GET("api/subjects/geteachsubject/{id}")
    suspend fun getSubjectById(@Path("id") subjectId: String): Response<SubjectDetailResponse>
    // User Functions
    @GET("api/users/getallusers")
    suspend fun getAllUsers(): Response<UserList>
    @POST("api/users/createoneuser")
    suspend fun createUser(@Body user: UserData): Response<UserResponse>
    @GET("api/users/getoneuser/{id}")
    suspend fun getUserById(@Path("id") userId: String): Response<UserResponse>

    @GET("api/users/getuserbyemail/{email}")
    suspend fun getUserbyEmail(@Path("email") email: String): Response<UserResponse>

    @GET("api/subjects/getallsubjects")
    suspend fun getAllSubjects(): Response<SubjectResponse>

    @POST("api/subjects/createsubject")
    suspend fun createSubject(@Body newSubject: NewSubjectRequest): Response<CreateSubjectResponse>

    @PUT("api/subjects/updatelikes/{id}")
    suspend fun updateLikes(@Path("id") subjectId: String, @Body requestBody: Map<String, Int>): Response<SubjectDetailResponse>


}


