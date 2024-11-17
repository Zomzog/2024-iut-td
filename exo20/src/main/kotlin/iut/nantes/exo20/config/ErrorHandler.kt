package iut.nantes.exo20.config

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorHandler: ResponseEntityExceptionHandler() {

//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException) =
//        ResponseEntity.internalServerError().body("You're arg is invalid")
}