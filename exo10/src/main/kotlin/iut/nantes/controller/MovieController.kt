package iut.nantes.controller

import iut.nantes.Database
import iut.nantes.Movie
import org.apache.catalina.loader.ResourceEntry
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerRequest.Headers

@RestController
class MovieController(val database: Database) {

    @PostMapping("/api/movies")
    fun create(@RequestBody movie: Movie) =
        if (database.getOne(movie.name) == null) {
            database.createMovie(movie).let {
                ResponseEntity.status(HttpStatus.CREATED).body(it)
            }
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Movie already exists")
        }

}