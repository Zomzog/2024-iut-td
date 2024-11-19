package iut.nantes.exo20.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Test

@WebMvcTest(PetController::class)
class PetControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var petController: PetController

    @BeforeEach
    fun setup() {
        val database = petController.database // in real production code, database should be private
        database.clear()
        database[1] = PetDto(1, "Kraken", 2008, PetKind.OCTOPUS)
        database[2] = PetDto(2, "Maurice", 1, PetKind.FISH)
    }

    @Nested
    inner class Exo20 {
        @Test
        fun `happy path`() {
            mockMvc.get("/api/v1/pets/1")
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$.name") { value("Kraken") }
                }
        }

        @Test
        fun `error case`() {

            mockMvc.get("/api/v1/pets/1")
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }
}