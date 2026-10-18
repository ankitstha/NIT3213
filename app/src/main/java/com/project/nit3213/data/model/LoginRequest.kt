package com.project.nit3213.data.model

// Data model representing the login request payload sent to the API
data class LoginRequest(
    val username: String, // Student ID
    val password: String  // Student Name
)

