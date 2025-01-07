package iut.nantes.exo33.controller

import iut.nantes.exo33.DatabaseProxy
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HumanController(val db: DatabaseProxy){

    @GetMapping("/api/v1/human/{human}")
    fun getHuman(@PathVariable humanId: Int) = db.findHumanById(humanId)?.let {
        ResponseEntity.ok(it)
    } ?: ResponseEntity.notFound().build()

    @PostMapping("/api/v1/humans")
    fun createHuman(@RequestBody human: HumanDto): ResponseEntity<HumanDto> {
        val withId = db.saveHuman(human)
        return ResponseEntity.status(HttpStatus.CREATED).body(withId)
    }

    @PutMapping("/api/v1/humans/{humanId}")
    fun updateHuman(@RequestBody human: HumanDto, @PathVariable humanId: Int): ResponseEntity<HumanDto> {
        if (human.humanId != humanId) {
            throw IllegalArgumentException("Human ID in path and body do not match")
        }
        val previous = db.findHumanById(humanId)
        if (previous == null) {
            throw (IllegalArgumentException("Human with ID $humanId not found"))
        } else {
            db.saveHuman(human).let { return ResponseEntity.ok(it) }
        }
    }

    @DeleteMapping("/api/v1/humans/{humanId}/pets/{petId}")
    fun removePet(@PathVariable humanId: Int, @PathVariable petId: Int): ResponseEntity<Unit> {
        db.deletePet(petId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/api/v1/humans")
    fun getHumans(@RequestParam minAge: Int?, @RequestParam maxAge: Int?): ResponseEntity<List<HumanDto>> {
        val result = db.findAllHuman()
        return ResponseEntity.ok(result)
    }
}