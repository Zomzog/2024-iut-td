package iut.nantes

import org.springframework.stereotype.Repository

@Repository
class Database {
    private val movies = mutableMapOf<String, Movie>().also { m ->
        MOVIES.forEach{ m[it.name] = it }
    }

    fun addMovie(movie: Movie) {
        if (movies.containsKey(movie.name)) {
            throw IllegalArgumentException("Movie already exists")
        }
        movies[movie.name] = movie
    }

    fun listMovies() = movies.values.toList()
    fun getMovie(name: String): Movie? {
        return movies[name]
    }

}