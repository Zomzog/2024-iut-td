package iut.nantes.exo20.controller

import org.hamcrest.Matchers.startsWith
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import kotlin.test.Test

@WebMvcTest
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

            mockMvc.get("/api/v1/pets/0")
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    inner class Exo21 {
        @Test
        fun `happy path`() {
            mockMvc.post("/api/v1/pets") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "name": "Kraken",
                        "age": 2008,
                        "kind": "OCTOPUS"
                    }
                """.trimIndent()
            }
                .andExpect {
                    status { isCreated() }
                    content { contentType("application/json") }
                    jsonPath("$.name") { value("Kraken") }
                }
        }

        @Test
        fun `error case invalid age`() {
            mockMvc.post("/api/v1/pets") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "name": "Egg",
                        "age": -1,
                        "kind": "FISH"
                    }
                """.trimIndent()
            }
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `error case invalid name`() {
            mockMvc.post("/api/v1/pets") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "name": "",
                        "age": 1,
                        "kind": "FISH"
                    }
                """.trimIndent()
            }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    inner class Exo22 {

        @Test
        fun `happy path`() {
            mockMvc.put("/api/v1/pets/1") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "id": 1,
                        "name": "Kraken",
                        "age": 3210,
                        "kind": "OCTOPUS"
                    }
                """.trimIndent()
            }
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$.name") { value("Kraken") }
                    jsonPath("$.age") { value("3210") }
                }
        }

        @Test
        fun `invalid path id`() {
            mockMvc.put("/api/v1/pets/0") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "id": 1,
                        "name": "Kraken",
                        "age": 3210,
                        "kind": "OCTOPUS"
                    }
                """.trimIndent()
            }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    inner class Exo23 {
        @Test
        fun `happy path`() {
            mockMvc.get("/api/v1/pets?minAge=2000&maxAge=2010")
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$[0].name") { value("Kraken") }
                }
        }

        @Test
        fun `max only`() {
            mockMvc.get("/api/v1/pets?maxAge=2010")
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$[0].name") { value("Kraken") }
                    jsonPath("$[1].name") { value("Maurice") }
                }
        }
    }

    @Nested
    inner class Exo24 {
        @Test
        fun `happy path`() {
            mockMvc.get("/api/v1/pets?minAge=2011&maxAge=2010")
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `min equals max`() {
            mockMvc.get("/api/v1/pets?minAge=1&maxAge=1")
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$[0].name") { value("Maurice") }
                }
        }
    }


    @Nested
    inner class Exo25 {

        @Test
        fun `happy path`() {
            mockMvc.put("/api/v1/pets/8") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "id": 8,
                        "name": "Kraken",
                        "age": 3210,
                        "kind": "OCTOPUS"
                    }
                """.trimIndent()
            }
                .andExpect {
                    status { isIAmATeapot() }
                }
        }
    }

    @Nested
    inner class Exo26 {
        @Test
        fun `error case invalid age`() {
            mockMvc.post("/api/v1/pets") {
                contentType = APPLICATION_JSON
                content = """
                    {
                        "name": "Pinkie Pie",
                        "age": 100,
                        "kind": "PONY"
                    }
                """.trimIndent()
            }.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType("application/json") }
                    jsonPath("$.status") { value("400") }
                    jsonPath("$.message") { startsWith("JSON parse error") }
                    jsonPath("$.title") { doesNotExist() }
                }
        }
    }
}