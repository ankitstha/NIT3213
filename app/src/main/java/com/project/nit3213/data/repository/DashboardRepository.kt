package com.project.nit3213.data.repository

import com.project.nit3213.data.model.DashboardResponse
import retrofit2.Response

interface DashboardRepository {
    suspend fun getDashboard(keypass: String): Response<DashboardResponse>
}
