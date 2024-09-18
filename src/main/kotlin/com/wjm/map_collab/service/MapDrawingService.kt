package com.wjm.map_collab.service

import com.bedatadriven.jackson.datatype.jts.parsers.GenericGeometryParser
import com.fasterxml.jackson.databind.JsonNode
import org.locationtech.jts.geom.GeometryFactory
import com.wjm.map_collab.entity.MapDrawing
import org.springframework.stereotype.Service
import com.wjm.map_collab.entity.Maps
import com.wjm.map_collab.repository.MapDrawingRepository
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.wololo.jts2geojson.GeoJSONReader

@Service
class MapDrawingService(private val mapDrawingRepository: MapDrawingRepository) {

    fun insertDrawings(dataArray: Array<JsonNode>, mapId: String) {
        val reader = GeoJSONReader()

        for (jsonElement in dataArray) {

            val id = jsonElement.get("id").asText()
            val geometry = reader.read(jsonElement.get("geometry").toString())

            val mapDrawing = MapDrawing(
                id = id,
                shape = geometry,
                mapId = mapId
            )
            try {
                mapDrawingRepository.save(mapDrawing)
            } catch (e: Exception) {

            }

            println("Inserted drawing with shape: ${jsonElement.get("geometry")} for map: $mapId")
        }
    }
    fun deleteDrawings(dataArray: Array<JsonNode>) {
        for (jsonElement in dataArray) {
            val id = jsonElement.get("id").asText()
            val drawing = mapDrawingRepository.findById(id)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Drawing not found") }
            mapDrawingRepository.delete(drawing)
        }
    }
}