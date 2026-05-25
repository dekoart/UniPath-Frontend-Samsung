package com.example.unipathapp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.unipathapp.models.LoginRequest
import com.example.unipathapp.models.RegisterRequest

interface AuthApi {

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<String>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<String>
}