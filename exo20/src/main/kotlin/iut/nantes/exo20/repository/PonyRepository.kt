package iut.nantes.exo20.repository

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class PonyRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager
    fun save(pony: Pony) {
//        entityManager.transaction.begin()
        entityManager.persist(pony)
//        entityManager.transaction.commit()
//        entityManager.close()
    }

    fun findAll(): List<Pony> {
        return entityManager.createQuery("SELECT pony from Pony pony", Pony::class.java)
            .getResultList();
    }
}

@Entity
class Pony(
    @Id @GeneratedValue
    val id: Long?,
    val name: String,
    val kind: String,
)
