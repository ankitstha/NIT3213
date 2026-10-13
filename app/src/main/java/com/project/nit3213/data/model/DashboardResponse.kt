package com.project.nit3213.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName(value = "entities", alternate = ["data", "items", "results"])
    val entities: List<EntityItem>?,
    @SerializedName(value = "entityTotal", alternate = ["total", "count", "entity_total"])
    val entityTotal: Int?
)
