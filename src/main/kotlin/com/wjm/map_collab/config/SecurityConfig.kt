package com.wjm.map_collab.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Throws(Exception::class)
    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{csrf -> csrf.disable()}
            .authorizeHttpRequests { requests ->
            requests
                .anyRequest()
                .permitAll()
        }
        return http.build()
    }
}