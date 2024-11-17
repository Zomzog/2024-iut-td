package iut.nantes.exo20.repository

import iut.nantes.exo20.domain.Movie
import org.springframework.stereotype.Repository

@Repository
class Database {
    private val movies = mutableMapOf<String, Movie>()


}