package iut.nantes

import java.util.*

class HashDatabase : Database{
    private val users = mutableMapOf<UUID, User>()
    override fun save(user: User) {
        users[user.id] = user
    }
    override fun findOne(id: UUID): User? {
        return users[id]
    }

    override fun findAll(name: String?): List<User> {
        if (name == null) {
            return users.values.toList()
        } else {
            return users.values.filter { it.name == name }
        }
    }

    override fun delete(user: User) {
        users.remove(user.id)
    }

    override fun update(user: User) {
        users[user.id] = user
    }
}