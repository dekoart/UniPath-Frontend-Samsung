package com.example.unipathapp.api

import com.example.unipathapp.models.ScoresRequest
import com.example.unipathapp.models.ScoresResponse
import retrofit2.Response
import retrofit2.http.*

interface ScoresApi {
    @POST("/api/scores")
    suspend fun saveScores(@Body request: ScoresRequest): Response<String>

    @GET("/api/scores/{userId}")
    suspend fun getScores(@Path("userId") userId: Long): Response<ScoresResponse>
}