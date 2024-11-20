package iut.nantes.exo20.controller

data class PetDto(val id: Int?, val name: String, val age: Int, val kind: PetKind) {
}

enum class PetKind {
    CAT, DOG, FISH, OCTOPUS
}