package com.project.nit3213.data.repository

import com.project.nit3213.data.model.LoginRequest
import com.project.nit3213.data.model.LoginResponse
import com.project.nit3213.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(username, password))
    }
}
