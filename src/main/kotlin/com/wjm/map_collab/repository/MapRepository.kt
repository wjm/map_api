package com.wjm.map_collab.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.wjm.map_collab.entity.Maps

@Repository
interface MapRepository : JpaRepository<Maps, String> {
    fun findByUserId(userId: String): List<Maps>
}