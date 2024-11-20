package iut.nantes.exo33.config

import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(e: ConstraintViolationException) = ResponseEntity.badRequest().body("Failure: ${e.message}")
    
    @ExceptionHandler(Exception::class)
    fun fallback(e: Exception) = ResponseEntity.internalServerError().body("Failure: ${e.message}")
}