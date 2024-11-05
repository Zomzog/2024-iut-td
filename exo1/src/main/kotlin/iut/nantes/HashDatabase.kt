package iut.nantes

import java.util.*
import org.springframework.stereotype.Repository

@Repository
class HashDatabase : Database {
    override fun save(user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(user: User) {
        TODO("Not yet implemented")
    }

    override fun update(user: User) {
        TODO("Not yet implemented")
    }

    override fun findOne(id: UUID): User? {
        TODO("Not yet implemented")
    }

    override fun findAll(name: String?): List<User> {
        TODO("Not yet implemented")
    }
}