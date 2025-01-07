package iut.nantes.exo33.controller

//data class HumanDto(val id: Int?, val name: String)

data class HumanDto(val humanId: Int?, val name: String, val contact: ContactDto, val pets: List<PetDto>)

data class ContactDto(val email: String)
