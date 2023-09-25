package com.example.demoproblem

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange
import org.zalando.problem.Problem
import org.zalando.problem.jackson.ProblemModule
import org.zalando.problem.spring.webflux.advice.ProblemHandling
import org.zalando.problem.violations.ConstraintViolationProblemModule
import reactor.core.publisher.Mono
import java.util.*

@Configuration
class ProblemConfiguration {
    @Value("\${server.error.include-stacktrace}")
    lateinit var includeStackTrace: String

    @Bean fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerModule(
                ProblemModule().withStackTraces(
                    includeStackTrace.lowercase(Locale.getDefault()) == "always"
                )
            )
            //.registerModule(ConstraintViolationProblemModule())
    }

    @Bean
    fun problemModule(): ProblemModule {
        return ProblemModule()
    }

    @Bean
    fun constraintViolationProblemModule(): ConstraintViolationProblemModule {
        return ConstraintViolationProblemModule()
    }

    @ControllerAdvice
    class ExceptionHandling : ProblemHandling {
        /*
        @ExceptionHandler
        override fun handleThrowable(
            throwable: Throwable,
            request: ServerWebExchange,
        ): Mono<ResponseEntity<Problem>> {
            return create(throwable, request)
        }
        */
        @ExceptionHandler
        override fun handleResponseStatusException(
            throwable: org.springframework.web.server.ResponseStatusException,
            request: ServerWebExchange,
        ): Mono<ResponseEntity<Problem>> {
            val result = create(throwable, request)
            //val stack = createStackTrace(throwable)
            return result.map { it }
        }
    }
}