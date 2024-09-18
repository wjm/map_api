package com.wjm.map_collab.controller

import com.wjm.map_collab.entity.User
import com.wjm.map_collab.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    data class LoginRequest(val username: String, val password: String)

    data class LoginResponse(val username: String, val userId: String, val company: String?)

    data class RegisterRequest(val username: String, val password: String)

    data class RegisterResponse(val message: String)

    data class ErrorResponse(val error: String, val reason: String)

    data class CompanyRequest(val company: String)
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val user: User = userService.login(request.username, request.password)

        val response = LoginResponse(username = user.username, userId = user.id ,company = user.company?: null)
        print(response)
        return ResponseEntity.ok(response)
    }
    @PostMapping("/createCompany")
    fun createCompany(@RequestHeader("userId") userId: String): ResponseEntity<Any> {
        return try {
            val user = userService.createCompany(userId)
            val response = LoginResponse(username = user.username, userId = user.id, company = user.company)
            ResponseEntity.ok(response)
        } catch (e: ResponseStatusException) {
            val errorResponse = ErrorResponse("error", e.reason ?: "Unknown error")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }
    }
    @PostMapping("/joinCompany")
    fun joinCompany(@RequestHeader("userId") userId: String, @RequestBody companyRequest: CompanyRequest): ResponseEntity<Any> {
        return try {
            val user = userService.joinCompany(companyRequest.company, userId)
            val response = LoginResponse(username = user.username, userId = user.id, company = user.company)
            ResponseEntity.ok(response)
        } catch (e: ResponseStatusException) {
            val errorResponse = ErrorResponse("error", e.reason ?: "Unknown error")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }
    }
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterRequest): ResponseEntity<Any> {
        return try {
            // Call user service to register the user
            val newUser = userService.register(request.username, request.password)

            // Return success message
            val response = RegisterResponse("User ${newUser.username} registered successfully!")
            ResponseEntity.ok(response)
        } catch (e: ResponseStatusException) {
            // Return error message (user exists)
            val errorResponse = ErrorResponse("error", e.reason ?: "Unknown error")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }
    }

}