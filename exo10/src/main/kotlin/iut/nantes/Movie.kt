package iut.nantes

data class Movie(val name: String, val releaseDate: Int, val rating: Int, val languages: List<String>)

val MOVIES = listOf(
    Movie("The Dark Knight", 2008, 9, listOf("VO")),
    Movie("Inception", 2010, 8, listOf("VF")),
    Movie("My Little Pony", 2017, 99, listOf("VO", "VFF"))
)