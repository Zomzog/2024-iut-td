package iut.nantes

import java.util.*

class UserService(val database: Database) {

    fun save(user: User) {
        database.save(user)
    }

    fun delete(user: User) {
        database.delete(user)
    }

    fun update(user: User) {
        TODO()
    }

    fun findOne(id: UUID): User? {
        return database.findOne(id)
    }

    fun findAll(name: String?): List<User> {
        TODO()
    }
}