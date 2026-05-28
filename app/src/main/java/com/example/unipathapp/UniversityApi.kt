package com.example.unipathapp.api

import com.example.unipathapp.models.UniversityResponse
import com.example.unipathapp.models.UniversityFilterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UniversityApi {
    @POST("/api/universities/search")
    suspend fun searchUniversities(@Body filters: UniversityFilterRequest): Response<List<UniversityResponse>>

    @GET("/api/universities")
    suspend fun getAllUniversities(): Response<List<UniversityResponse>>
}