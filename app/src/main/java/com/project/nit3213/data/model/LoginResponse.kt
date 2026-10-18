package com.project.nit3213.data.model

import com.google.gson.annotations.SerializedName

// Data model representing the response returned from authentication API
data class LoginResponse(
    @SerializedName("keypass")
    val keypass: String? // Keypass token used to access dashboard
)

