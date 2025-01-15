package iut.nantes

import assertk.assertThat
import assertk.assertions.containsExactly
import java.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListDatabaseTest {
    var database = ListDatabase()
    @BeforeEach
    fun setup() {
        database = ListDatabase()
    }

    @Test
    fun `save should add user to database`() {
        // GIVEN
        val user = user()
        // WHEN
        database.save(user)
        // THEN
        assertEquals(user, database.findOne(user.id))
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