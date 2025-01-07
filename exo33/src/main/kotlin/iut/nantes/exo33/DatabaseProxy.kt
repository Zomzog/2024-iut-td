package iut.nantes.exo33

import iut.nantes.exo33.controller.ContactDto
import iut.nantes.exo33.controller.HumanDto
import iut.nantes.exo33.controller.PetDto
import iut.nantes.exo33.controller.PetKind
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class DatabaseProxy(private val petJpa: PetJpa, val humanJpa: HumanJpa) {
    fun savePet(pet: PetDto): PetDto {
        val h = humanJpa.findAll().first()
        val e = PetEntity(pet.id, pet.name, pet.age, pet.kind, h)
        petJpa.save(e).let { return PetDto(it.petId, it.name, it.age, it.kind) }
    }

    fun findPetById(id: Int): PetDto? {
        return petJpa.findById(id).map { PetDto(it.petId, it.name, it.age, it.kind) }.orElse(null)
    }

    fun findAllPets(): List<PetDto> {
        return petJpa.findAll().map { PetDto(it.petId, it.name, it.age, it.kind) }
    }

    fun saveHuman(humanDto: HumanDto): HumanDto {
        val h = HumanEntity(humanDto.humanId, humanDto.name)
        val contact = ContactEntity(h, humanDto.contact.email)
        h.contact = contact
        val pets = humanDto.pets.map { petDto ->
            PetEntity(petDto.id, petDto.name, petDto.age, petDto.kind, h)
        }
        h.pets = pets
        return toDto(humanJpa.save(h))
    }

    fun findHumanById(id: Int): HumanDto? {
        return humanJpa.findById(id).map { toDto(it) }.orElse(null)
    }

    fun findAllHuman(): List<HumanDto> {
        return humanJpa.findAll().map { toDto(it) }
    }

    private fun toDto(it: HumanEntity) = HumanDto(it.humanId, it.name, ContactDto(it.contact!!.email), it.pets.map { pet -> PetDto(pet.petId, pet.name, pet.age, pet.kind) })

    fun deletePet(petId: Int) {
        petJpa.deleteById(petId)
    }
}

interface PetJpa : JpaRepository<PetEntity, Int> {
}

interface HumanJpa : JpaRepository<HumanEntity, Int> {
}

@Entity
data class PetEntity(
    @Id @GeneratedValue val petId: Int?, val name: String, val age: Int, val kind: PetKind,
    @ManyToOne
    @JoinColumn(name = "human_id")
    val human: HumanEntity
)

@Entity
data class HumanEntity(
    @Id @GeneratedValue val humanId: Int?,
    val name: String,
    @OneToOne(mappedBy = "human", cascade = [CascadeType.ALL])
    var contact: ContactEntity? = null,
    @OneToMany(mappedBy = "human", cascade = [CascadeType.ALL])
    var pets: List<PetEntity> = mutableListOf()
)

@Entity
data class ContactEntity(
    @OneToOne @Id
    val human: HumanEntity,
    val email: String
)