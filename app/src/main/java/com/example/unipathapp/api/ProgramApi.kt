package com.example.unipathapp.api

import com.example.unipathapp.models.ProgramFilterRequest
import com.example.unipathapp.models.ProgramResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProgramApi {
    @POST("/api/programs/filter")
    suspend fun filterPrograms(@Body request: ProgramFilterRequest): Response<List<ProgramResponse>>
}