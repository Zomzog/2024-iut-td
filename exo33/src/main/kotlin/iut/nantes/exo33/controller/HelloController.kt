package iut.nantes.exo33.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class HelloController {

    @GetMapping("/")
    fun root(principal: Principal) = ResponseEntity.status(HttpStatus.OK).body("Hello, ${principal.name}")

    @GetMapping("/admin")
    fun admin(principal: Principal) = ResponseEntity.status(HttpStatus.OK).body("Hello admin, ${principal.name}")

    @GetMapping("/ponies")
    fun pony() = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Rainbow Dash")
}