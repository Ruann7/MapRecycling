package com.example.maprecycling.data.remote

import retrofit2.http.GET

data class RecyclingApiPoint(
    val lat: Double,
    val lng: Double,
    val title: String,
    val description: String,
    val imageUrl: String
)

interface ApiService {
    @GET("recycling_points.json")
    suspend fun getRecyclingPoints(): List<RecyclingApiPoint>
}