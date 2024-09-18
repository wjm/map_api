package com.wjm.map_collab.entity
import com.fasterxml.jackson.databind.JsonNode


data class DrawMessage(
    var data: Array<JsonNode>? = null,
    var type: String? = null,
)