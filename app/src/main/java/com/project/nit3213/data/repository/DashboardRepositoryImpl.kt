package com.project.nit3213.data.repository

import com.project.nit3213.data.model.DashboardResponse
import com.project.nit3213.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

// Concrete implementation of DashboardRepository calling ApiService
class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService // Injected Retrofit API service
) : DashboardRepository {

    // Fetches dashboard data from API using keypass
    override suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}

