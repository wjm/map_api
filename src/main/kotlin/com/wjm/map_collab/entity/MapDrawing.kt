package com.wjm.map_collab.entity

import jakarta.persistence.*
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory

@Entity
@Table(name = "map_drawing")
data class MapDrawing(
    @Id
    @Column(nullable = false, unique = true)
    val id: String = "",

    @Column(nullable = false, columnDefinition = "geometry")
    val shape: Geometry = GeometryFactory().createPoint(),

    @Column(nullable = false)
    val mapId: String = ""
)