package com.example.demoproblem

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class RestController(
    // Yes, we are aware this is unused.... it ios here for debugging purposes
    val objectMapper: ObjectMapper
) {
    @GetMapping("/test")
    fun getTest(): ResponseEntity<String> {
        throw ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "test")
    }
}