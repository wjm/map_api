package com.wjm.map_collab.service

import com.wjm.map_collab.repository.MapRepository
import org.springframework.stereotype.Service
import com.wjm.map_collab.entity.Maps
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Service
class MapService(private val mapRepository: MapRepository, private val userService: UserService) {

    fun getMapsByUserId(userId: String): List<Maps> {
        return mapRepository.findByUserId(userId)
    }
    fun getMapsByCompany(userId: String): List<Maps> {
        val user = userService.findUserById(userId) ?:
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist")
        val userCompany = user.company ?: return emptyList()
        val users = userService.findByCompany(userCompany)
        val userIds = users.map { user -> user.id }.filter { it != userId }
        return mapRepository.findAll().filter { it.userId in userIds }
    }

    fun createMap(userId: String, mapName: String): Maps {
        val userExists = userService.findUserById(userId)

        if (userExists == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist")
        }
        val newMap = Maps(
            mapName = mapName,
            userId = userId
        )

        return mapRepository.save(newMap)
    }
    fun checkMap(userId: String, mapId: String): Maps {
        val map = mapRepository.findById(mapId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Map does not exist")
        }

        if (map.userId != userId) {
            val mapOwnerCompany = userService.findUserById(map.userId)?.company
            val userCompany = userService.findUserById(userId)?.company
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist")

            if (mapOwnerCompany != userCompany) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Map does not belong to user")
            }
        }

        return map
    }
}