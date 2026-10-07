package com.project.nit3213.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("keypass")
    val keypass: String?
)
