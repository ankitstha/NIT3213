package com.project.nit3213.data.model

import com.google.gson.*
import java.io.Serializable
import java.lang.reflect.Type

// Data class representing an entity item with dynamic attribute key-value pairs
data class EntityItem(
    val attributes: Map<String, String?> = emptyMap() // Holds dynamic properties parsed from JSON
) : Serializable {

    // Secondary constructor for unit testing and manual object creation
    constructor(property1: String?, property2: String?, description: String?) : this(
        mapOf(
            "property1" to property1,
            "property2" to property2,
            "description" to description
        ).filterValues { it != null }.mapValues { it.value!! }
    )

    // Gets the main property (checks common property keys or falls back to the first entry)
    val property1: String?
        get() = attributes["property1"] 
            ?: attributes["name"] 
            ?: attributes["title"] 
            ?: attributes["id"] 
            ?: attributes.values.firstOrNull()

    // Gets the secondary property (checks common keys or falls back to the second entry)
    val property2: String?
        get() = attributes["property2"] 
            ?: attributes["subtitle"] 
            ?: attributes["value"] 
            ?: attributes["detail"] 
            ?: attributes.values.drop(1).firstOrNull()

    // Gets the description property (checks common description keys or falls back to third entry)
    val description: String?
        get() = attributes["description"] 
            ?: attributes["desc"] 
            ?: attributes["details"] 
            ?: attributes["content"] 
            ?: attributes["summary"] 
            ?: attributes.values.drop(2).firstOrNull()
}

// Custom Gson deserializer to parse dynamic/varying JSON object keys into a map
class EntityItemDeserializer : JsonDeserializer<EntityItem> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EntityItem {
        val map = mutableMapOf<String, String?>()
        if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            // Iterate through every dynamic property key in the JSON object
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

