package iut.nantes

class SuperUserService(val database: Database) {
    fun findAll(): List<User> {
        return database.findAll(null)
    }
}