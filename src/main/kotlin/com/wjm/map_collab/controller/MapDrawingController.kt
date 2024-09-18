package com.wjm.map_collab.controller

import com.wjm.map_collab.controller.MapController.ErrorResponse
import com.wjm.map_collab.dto.MapDrawingResponse
import com.wjm.map_collab.repository.MapDrawingRepository
import com.wjm.map_collab.service.MapService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api")
class MapDrawingController(private val mapDrawingRepository: MapDrawingRepository, private val mapService: MapService) {

    @GetMapping("/drawings/{mapId}")
    fun getDrawingsForMap(
        @PathVariable("mapId") mapId: String,
        @RequestHeader("userId",required = false) userId: String?
    ): ResponseEntity<List<MapDrawingResponse>> {
        if (userId.isNullOrBlank()) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")
        }
        try {
            val mapResult = mapService.checkMap(userId, mapId)
            val drawings = mapDrawingRepository.findAllByMapId(mapId)

            val responseList = drawings.map { drawing ->
                MapDrawingResponse.fromEntity(drawing.id, drawing.mapId, drawing.shape)
            }
            return if (responseList.isNotEmpty()) {
                ResponseEntity.ok(responseList)
            } else {
                ResponseEntity.noContent().build()
            }
        } catch (e: ResponseStatusException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.reason ?: "Unknown error")
        }
    }
}