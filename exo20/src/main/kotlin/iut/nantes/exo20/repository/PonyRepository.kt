package iut.nantes.exo20.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.NamedQuery
import jakarta.persistence.PersistenceContext
import jakarta.persistence.PersistenceUnit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface PonyRepository: JpaRepository<Pony, Long>

//@Repository
//class PonyRepository {
//
//    @PersistenceContext
//    private lateinit var entityManager: EntityManager
//
//    @Transactional
//    fun save(pony: Pony) = entityManager.persist(pony)
//
//    fun findAll() = entityManager.createNamedQuery("Pony.findAll", Pony::class.java)
//        .resultList

//    fun findAll() = entityManager.createQuery("select pony from pony pony", Pony::class.java)
//        .resultList

//    @PersistenceUnit
//    private lateinit var factory: EntityManagerFactory
//
//    fun save(pony: Pony) = factory.createEntityManager().use { em ->
//        em.transaction.begin()
//        em.persist(pony)
//        em.transaction.commit()
//    }
//
//    fun findAll(): List<Pony> = factory.createEntityManager().use { em ->
//        em.createQuery("SELECT pony from Pony pony", Pony::class.java)
//            .getResultList();
//    }

//}

@Entity
@NamedQuery(name = "Pony.findAll", query = "SELECT p FROM Pony p")
class Pony(
    @Id @GeneratedValue
    val id: Long?,
    @Column(name = "n", nullable = false, unique = false)
    val name: String,
    val kind: String,
)
