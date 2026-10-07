package com.project.nit3213.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("entities")
    val entities: List<EntityItem>?,
    @SerializedName("entityTotal")
    val entityTotal: Int?
)
