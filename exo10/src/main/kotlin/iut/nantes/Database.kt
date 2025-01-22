package iut.nantes

import org.springframework.stereotype.Repository

@Repository
class Database {
    private val movies = mutableMapOf<String, Movie>()

    fun createMovie(movie: Movie): Movie {
        movies[movie.name] = movie
        return movie
    }

    fun getOne(name: String) = movies[name]

    fun getMovies() = movies.values

    fun update(movie: Movie): Movie {
        movies[movie.name] = movie
        return movie
    }
    fun delete(name:String) {
        movies.remove(name)
    }
}