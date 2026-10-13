package com.project.nit3213.data.model

import com.google.gson.*
import java.io.Serializable
import java.lang.reflect.Type

/**
 * EntityItem dynamically handles any custom entity JSON structure returned per student from the API.
 */
data class EntityItem(
    val attributes: Map<String, String?> = emptyMap()
) : Serializable {

    // Secondary constructor for backward compatibility and unit tests
    constructor(property1: String?, property2: String?, description: String?) : this(
        mapOf(
            "property1" to property1,
            "property2" to property2,
            "description" to description
        ).filterValues { it != null }.mapValues { it.value!! }
    )

    // Dynamically retrieve property 1 (checks common keys or falls back to first attribute)
    val property1: String?
        get() = attributes["property1"] 
            ?: attributes["name"] 
            ?: attributes["title"] 
            ?: attributes["id"] 
            ?: attributes.values.firstOrNull()

    // Dynamically retrieve property 2 (checks common keys or falls back to second attribute)
    val property2: String?
        get() = attributes["property2"] 
            ?: attributes["subtitle"] 
            ?: attributes["value"] 
            ?: attributes["detail"] 
            ?: attributes.values.drop(1).firstOrNull()

    // Dynamically retrieve description
    val description: String?
        get() = attributes["description"] 
            ?: attributes["desc"] 
            ?: attributes["details"] 
            ?: attributes["content"] 
            ?: attributes["summary"] 
            ?: attributes.values.drop(2).firstOrNull()
}

/**
 * Custom Gson Deserializer to parse arbitrary/dynamic keys into attributes.
 */
class EntityItemDeserializer : JsonDeserializer<EntityItem> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EntityItem {
        val map = mutableMapOf<String, String?>()
        if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            for ((key, value) in jsonObject.entrySet()) {
                if (value != null && !value.isJsonNull) {
                    map[key] = if (value.isJsonPrimitive) value.asString else value.toString()
                } else {
                    map[key] = null
                }
            }
        }
        return EntityItem(map)
    }
}
