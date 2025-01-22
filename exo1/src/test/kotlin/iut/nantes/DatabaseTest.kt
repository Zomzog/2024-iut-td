package iut.nantes

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import java.util.*
import org.junit.jupiter.api.Test

abstract class DatabaseTest(val database: Database) {
    @Test
    fun `find all users`() {
        database.save(user())
        val users = database.findAll(null)
        assertThat(users).containsExactly(user())
    }

    @Test
    fun `save should add user to database`() {
        // GIVEN
        val user = user()
        // WHEN
        database.save(user)
        // THEN
        assertThat(database.findOne(user.id)).isEqualTo(user)
    }

    @Test
    fun `findAll should filter by name`() {
        // GIVEN
        val user1 = user(UUID.randomUUID(), name = "John")
        val user2 = user(UUID.randomUUID(), name = "Jane")
        val user3 = user(UUID.randomUUID(), name = "John")
        database.save(user1)
        database.save(user2)
        database.save(user3)
        // WHEN
        val result = database.findAll("John")
        // THEN
        assertThat(result.map { it.id }).containsExactly(user1.id, user3.id)
    }
}

class ListDatabaseTest: DatabaseTest(ListDatabase())
class HashDatabaseTest: DatabaseTest(HashDatabase())
