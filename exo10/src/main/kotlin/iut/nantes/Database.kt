package iut.nantes

import org.springframework.stereotype.Repository

@Repository
class Database {
    private val movies = mutableMapOf<String, Movie>()


}