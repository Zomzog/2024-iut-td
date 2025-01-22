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

    @GetMapping("/api/movies")
    fun list(@RequestParam(name = "rating") queryRating: List<Int>?,
             @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE) lang: String?): ResponseEntity<List<Movie>> {
        var all = database.getMovies().toList()
        if (queryRating != null) {
            all = all.filter { it.rating in queryRating }
        }
        if(lang != null) {
            all = all.filter { it.languages.contains( lang) }
        }
        return ResponseEntity.ok(all)
    }

   @GetMapping("/api/movies/{name}")
    fun getOne(@PathVariable name: String) = database.getOne(name)?.let {
        ResponseEntity.ok(it)
    } ?: ResponseEntity.notFound().build()

    @PutMapping("/api/movies/{name}")
    fun update(@PathVariable name: String, @RequestBody movie: Movie) =
        if (name != movie.name) {
            ResponseEntity.badRequest().body("Name in path and body must be the same")
        } else if (database.getOne(name) != null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Movie already exists")
        } else {
            database.update(movie).let {
                ResponseEntity.ok(it)
            }
        }

    @DeleteMapping("/api/movies/{name}")
    fun delete(@PathVariable name: String): ResponseEntity<Unit> = database.getOne(name)?.let {
        database.delete(it.name)
        ResponseEntity.noContent().build()
    } ?: ResponseEntity.notFound().build()
}