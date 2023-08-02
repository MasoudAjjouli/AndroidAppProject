package com.example.myapp.network

import com.example.myapp.models.UserInfo
import retrofit2.http.GET

interface RetroService {
    @GET("JJfCqp/data")
    suspend fun getDataFromApi(): UserInfo
}