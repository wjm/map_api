package com.wjm.map_collab.controller

import com.wjm.map_collab.service.MapService
import com.wjm.map_collab.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/map")
class MapController(private val mapService: MapService, private val userService: UserService) {

    data class CreateMapRequest(val user_id: String, val map_name: String)

    data class CreateMapResponse(val map_id: String?, val map_name: String)


    data class MapRequest(val user_id: String)
    data class MapResponse(val user_id: String, val maps: Map<String, List<MapData>>)
    data class MapData(val map_id: String?, val map_name: String)
    data class ErrorResponse(val error: String, val reason: String)
    data class CheckMapRequest(val user_id: String, val map_id: String)
    data class CheckMapResponse(val user_id: String, val map_id: String, val valid: Boolean)

    @PostMapping("/get")
    fun getMapsForUser(@RequestBody request: MapRequest): ResponseEntity<MapResponse> {
        val combainedMaps = mutableMapOf<String,List<MapData>>();
        val maps = mapService.getMapsByUserId(request.user_id).map { map ->
            MapData(map_id = map.id, map_name = map.mapName)
        }
        combainedMaps["user"] = maps
        val sharedMaps = mapService.getMapsByCompany(request.user_id).map { map ->
            MapData(map_id = map.id, map_name = map.mapName)
        }
        combainedMaps["shared"] = sharedMaps


        val response = MapResponse(user_id = request.user_id, maps = combainedMaps)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/create")
    fun createMap(@RequestBody request: CreateMapRequest): ResponseEntity<Any> {
        return try {
            val newMap = mapService.createMap(request.user_id, request.map_name)

            val response = CreateMapResponse(
                map_id = newMap.id,
                map_name = newMap.mapName,
            )
            ResponseEntity.ok(response)
        } catch (e: ResponseStatusException) {
            val errorResponse = ErrorResponse("error", e.reason ?: "Unknown error")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }
    }
    @PostMapping("/check")
    fun checkMap(@RequestBody request: CheckMapRequest): ResponseEntity<CheckMapResponse> {
        return try {
            val mapResult = mapService.checkMap(request.user_id, request.map_id)
            val response = CheckMapResponse(
                user_id = mapResult.userId,
                map_id = mapResult.id,
                valid = true
            )
            ResponseEntity.ok(response)
        } catch (e: ResponseStatusException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.reason ?: "Unknown error")
        }
    }
}
