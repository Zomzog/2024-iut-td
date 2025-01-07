package iut.nantes.exo33.controller

import iut.nantes.exo33.DatabaseProxy
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PetController(val db: DatabaseProxy){

    @GetMapping("/api/v1/pets/{petId}")
    fun getPet(@PathVariable petId: Int) = db.findPetById(petId)?.let {
        ResponseEntity.ok(it)
    } ?: ResponseEntity.notFound().build()

    @PostMapping("/api/v1/pets")
    fun createPet(@RequestBody pet: PetDto): ResponseEntity<PetDto> {
        db.savePet(pet).let { return ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @PutMapping("/api/v1/pets/{petId}")
    fun updatePet(@RequestBody pet: PetDto, @PathVariable petId: Int): ResponseEntity<PetDto> {
        if (pet.id != petId) {
            throw IllegalArgumentException("Pet ID in path and body do not match")
        }
        val previous = db.findPetById(petId)
        if (previous == null) {
            throw (IllegalArgumentException("Pet with ID $petId not found"))
        } else {
            db.savePet(pet)
        }
        return ResponseEntity.ok(pet)
    }

    @GetMapping("/api/v1/pets")
    fun getPets(): ResponseEntity<List<PetDto>> {
        val result = db.findAllPets()
        return ResponseEntity.ok(result)
    }
}