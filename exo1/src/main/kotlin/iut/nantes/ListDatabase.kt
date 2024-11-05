package iut.nantes

import java.util.UUID

class ListDatabase : Database {
    private val users = mutableListOf<User>()

    override fun save(user: User) {
        users.add(user)
    }

    override fun delete(user: User) {
        users.remove(user)
    }

    override fun update(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
    }

    override fun findOne(id: UUID): User? {
        return users.find { it.id == id }
    }

    override fun findAll(name: String?): List<User> {
        return if (name != null) {
            users.filter { it.name == name }
        } else {
            users
        }
    }
}