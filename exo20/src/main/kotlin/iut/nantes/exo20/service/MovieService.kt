package iut.nantes.exo20.service

import iut.nantes.exo20.domain.Movie
import iut.nantes.exo20.repository.MinMax
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

//@Service
@Validated
class MovieService() {

//    fun save(movie: Movie,
//             @Max(5) @Min(10) fake: Int) = println("$fake - $movie")

    fun save(movie: Movie,
             @MinMax(10, 12) fake: Int) = println("$fake - $movie")
}