package iut.nantes

import java.util.UUID

interface Database {

    /**
     * Save a user in the database
     */
    fun save(user: User)

    /**
     * Delete a user from the database
     */
    fun delete(user: User)

    /**
     * Update a user in the database
     */
    fun update(user: User)

    /**
     * Find a user by its id, return null if not found
     */
    fun findOne(id: UUID): User?

    /**
     * Find all users with the given name, return all users if name is null
     */
    fun findAll(name: String?): List<User>
}