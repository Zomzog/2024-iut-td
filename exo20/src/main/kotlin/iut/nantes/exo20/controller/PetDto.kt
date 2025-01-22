package iut.nantes.exo20.controller

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class PetDto(val id: Int?, @field:NotBlank val name: String, @field:Min(0) val age: Int, val kind: PetKind) {
}

enum class PetKind {
    CAT, DOG, FISH, OCTOPUS
}