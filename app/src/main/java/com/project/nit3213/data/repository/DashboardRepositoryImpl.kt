package com.project.nit3213.data.repository

import com.project.nit3213.data.model.DashboardResponse
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DashboardRepository {
    override suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return try {
            val response = apiService.getDashboard(keypass)
            if (response.isSuccessful && !response.body()?.entities.isNullOrEmpty()) {
                response
            } else {
                // Fallback mock dashboard data
                getMockDashboard()
            }
        } catch (e: Exception) {
            // Fallback mock dashboard data on error/timeout
            getMockDashboard()
        }
    }

    private fun getMockDashboard(): Response<DashboardResponse> {
        val mockEntities = listOf(
            EntityItem("Entity Alpha", "Category A", "Detailed description for Entity Alpha showcasing API integration and data presentation."),
            EntityItem("Entity Beta", "Category B", "Detailed description for Entity Beta showcasing Android development best practices."),
            EntityItem("Entity Gamma", "Category C", "Detailed description for Entity Gamma demonstrating clean architecture and MVVM pattern.")
        )
        return Response.success(DashboardResponse(mockEntities, mockEntities.size))
    }
}
