package com.example.maprecycling.data.repository

import com.example.maprecycling.data.remote.RetrofitInstance

class RecyclingRepository {
    suspend fun getPoints() = RetrofitInstance.api.getRecyclingPoints()
}