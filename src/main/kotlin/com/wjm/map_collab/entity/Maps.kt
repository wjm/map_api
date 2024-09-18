package com.wjm.map_collab.entity

import jakarta.persistence.*

@Entity
@Table(name = "maps")
data class Maps(
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String = "",

    @Column(nullable = false)
    val mapName: String = "",

    @Column(nullable = false)
    val userId: String = ""
)