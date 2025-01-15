package iut.nantes

import org.springframework.beans.factory.annotation.Autowired

class SuperUserService {
    @Autowired
    private lateinit var database: ListDatabase

    fun findAll(): List<User> {
        return database.findAll(null)
    }
}