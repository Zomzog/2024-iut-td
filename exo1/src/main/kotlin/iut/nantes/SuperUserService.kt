package iut.nantes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

class SuperUserService() {
    @Qualifier("theHashDb")
    @Autowired
    lateinit var database: Database

    fun findAll(): List<User> {
        return database.findAll(null)
    }
}