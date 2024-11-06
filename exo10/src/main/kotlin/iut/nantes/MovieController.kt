package iut.nantes

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerRequest.Headers

@RestController
class MovieController(val db: Database) {
    @PostMapping("/api/movies")
    fun createMovie(@RequestBody movie: Movie): ResponseEntity<Movie> {
        try {
            db.addMovie(movie)
            return ResponseEntity.status(HttpStatus.CREATED).body(movie)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }
    @GetMapping("/api/movies")
    fun listMovies(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) language: String?): List<Movie> {
        return db.listMovies()
    }

    @GetMapping("/api/movies/{name}")
    fun getMovie(@PathVariable name: String): ResponseEntity<Movie> {
        val movie = db.getMovie(name)
        return if (movie != null) {
            ResponseEntity.ok(movie)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}