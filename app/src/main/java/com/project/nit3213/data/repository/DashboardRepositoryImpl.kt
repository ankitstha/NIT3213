package com.project.nit3213.data.repository

import com.project.nit3213.data.model.DashboardResponse
import com.project.nit3213.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DashboardRepository {
    override suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}
