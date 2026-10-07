package com.project.nit3213.data.repository

import com.project.nit3213.data.model.DashboardResponse
import com.project.nit3213.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}
