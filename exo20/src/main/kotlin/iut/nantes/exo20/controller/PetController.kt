package iut.nantes.exo20.controller

import iut.nantes.exo20.errors.ImATeapotException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PetController(val database : MutableMap<Int, PetDto> = mutableMapOf()){

    @GetMapping("/api/v1/pets/{petId}")
    fun getPet(@PathVariable petId: Int) = database[petId]?.let {
        ResponseEntity.ok(it)
    } ?: ResponseEntity.notFound().build()


    @PostMapping("/api/v1/pets")
    fun createPet(@RequestBody pet: PetDto): ResponseEntity<PetDto> {
        val next = (database.keys.maxOrNull() ?: 0) + 1
        val withId = pet.copy(id = next)
        database[next] = withId
        return ResponseEntity.status(HttpStatus.CREATED).body(withId)
    }

    @PutMapping("/api/v1/pets/{petId}")
    fun updatePet(@RequestBody pet: PetDto, @PathVariable petId: Int): ResponseEntity<PetDto> {
        if (pet.id != petId) {
            throw IllegalArgumentException("Pet ID in path and body do not match")
        }
        val previous = database[petId]
        if (previous == null) {
            throw ImATeapotException()
        } else {
            database[petId] = pet
        }
        return ResponseEntity.ok(pet)
    }

    @GetMapping("/api/v1/pets")
    fun getPets(@RequestParam minAge: Int?, @RequestParam maxAge: Int?): ResponseEntity<List<PetDto>> {
       var result: List<PetDto> = database.values.toList()
        if (minAge != null) result = result.filter { it.age >= minAge }
        if (maxAge != null) result = result.filter { it.age <= maxAge }
        return ResponseEntity.ok(result)
    }
}