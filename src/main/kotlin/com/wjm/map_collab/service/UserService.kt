package com.wjm.map_collab.service

import com.wjm.map_collab.entity.User
import com.wjm.map_collab.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


@Service
class UserService(private val userRepository: UserRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()
    fun login(username: String, password: String): User {
        val user = userRepository.findByUsername(username)
            .orElseThrow { ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials") }
        if (!passwordEncoder.matches(password, user.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        return user
    }
    fun findUserById(userId: String): User? {
        return userRepository.findById(userId).orElse(null)
    }
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }
    fun findByCompany(company: String): List<User> {
        return userRepository.findByCompany(company)
    }

    fun createCompany(userId: String): User {
        val company = UUID.randomUUID().toString()
        var user = userRepository.findById(userId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        user.company?.takeIf { it.isNotBlank() }?.let {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a company")
        }
        user.company = company
        return userRepository.save(user)
    }

    fun joinCompany(company: String, userId: String): User {
        var user = userRepository.findById(userId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        user.company?.takeIf { it.isNotBlank() }?.let {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a company")
        }
        userRepository.findByCompany(company).ifEmpty {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "no such company")
        }
        user.company = company
        return userRepository.save(user)
    }
    fun register(username: String, password: String): User {
        // Check if username already exists
        userRepository.findByUsername(username).ifPresent {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists")
        }
        val hashedPassword = passwordEncoder.encode(password)
        val newUser = User(
            username = username,
            password = hashedPassword,
        )
        return userRepository.save(newUser)
    }
}