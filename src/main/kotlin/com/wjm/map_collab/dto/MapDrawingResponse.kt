package com.wjm.map_collab.dto

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.locationtech.jts.geom.Geometry
import org.wololo.jts2geojson.GeoJSONWriter

data class MapDrawingResponse(
    val id: String,
    //val mapId: String,
    val shape: JsonNode
) {
    companion object {
        fun fromEntity(id: String, mapId: String, geometry: Geometry): MapDrawingResponse {
            val geoJSONWriter = GeoJSONWriter()
            val geoJsonString = geoJSONWriter.write(geometry).toString()

            // Parse the GeoJSON string
            val objectMapper = ObjectMapper()
            val geoJsonNode = objectMapper.readTree(geoJsonString)

            return MapDrawingResponse(
                id = id,
                //mapId = mapId,
                shape = geoJsonNode
            )
        }
    }
}
