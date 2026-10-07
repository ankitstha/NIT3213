package com.project.nit3213.data.repository

import com.project.nit3213.data.model.LoginResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun login(username: String, password: String): Response<LoginResponse>
}
