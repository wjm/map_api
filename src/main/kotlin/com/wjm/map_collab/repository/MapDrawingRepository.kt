package com.wjm.map_collab.repository

import com.wjm.map_collab.entity.MapDrawing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MapDrawingRepository : JpaRepository<MapDrawing, String>{
    fun findAllByMapId(mapId: String): List<MapDrawing>
}