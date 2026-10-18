package com.project.nit3213.data.model

import com.google.gson.annotations.SerializedName

// Data model representing the dashboard response containing list of entities
data class DashboardResponse(
    // SerializedName with alternate keys handles different field names returned by the API
    @SerializedName(value = "entities", alternate = ["data", "items", "results"])
    val entities: List<EntityItem>?,
    
    @SerializedName(value = "entityTotal", alternate = ["total", "count", "entity_total"])
    val entityTotal: Int?
)

