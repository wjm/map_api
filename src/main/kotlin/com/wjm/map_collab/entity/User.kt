package com.wjm.map_collab.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String = "",

    @Column(nullable = false, unique = true)
    val username: String = "",

    @Column(nullable = false)
    val password: String = "",

    @Column(nullable = true)
    var company: String? = null,
)