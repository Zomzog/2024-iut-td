package iut.nantes.exo20.controller

import iut.nantes.exo20.domain.Movie
import iut.nantes.exo20.repository.Pony
import iut.nantes.exo20.repository.PonyRepository
import iut.nantes.exo20.service.MovieService
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(val movieService: MovieService,
    val ponyRepository: PonyRepository){

    @PostMapping("/movies")
    fun create(@RequestBody movie: Movie) {
        movieService.save(movie, movie.rating)
    }

    @PostMapping("/ponies")
    @Transactional
    fun createPonies(@RequestBody pony: Pony) {
       ponyRepository.save(pony)
    }

    @GetMapping("/ponies")
    @Transactional
    fun createPonies() = ponyRepository.findAll()
}