package iut.nantes

import java.util.*
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Repository

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Primary
class ListDatabase: Database {

    private val users = mutableListOf<User>()

    override fun save(user: User) {
        users.add(user)
    }

    override fun delete(user: User) {
        users.removeIf{ it.id == user.id }
    }

    override fun update(user: User) {
        users.removeIf{ it.id == user.id }
        users.add(user)
    }

    override fun findOne(id: UUID): User? = users.find { it.id == id }

    override fun findAll(name: String?): List<User> = if (name == null) {
        users
    } else {
        users.filter { it.name == name }
    }
}