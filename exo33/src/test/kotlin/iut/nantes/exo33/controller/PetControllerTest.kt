package iut.nantes.exo33.controller

import iut.nantes.exo33.DatabaseProxy
import org.hamcrest.Matchers.startsWith
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var databaseProxy: DatabaseProxy

    @BeforeEach
    fun setup() {
    }

    @Test
    fun `exo 35`() {
        mockMvc.post("/api/v1/humans") {
            contentType = APPLICATION_JSON
            content = humanJson("Joe")
        }.andExpect {
            status { isCreated() }
            content { contentType("application/json") }
            jsonPath("$.name") { value("Joe") }
        }
        mockMvc.get("/api/v1/humans") {
        }.andExpect {
            status { isOk() }
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Joe") }
        }
    }

    @Test
    fun `exo 36`() {
        mockMvc.post("/api/v1/humans") {
            contentType = APPLICATION_JSON
            content = humanJson("Joe")
        }.andExpect {
            status { isCreated() }
            content { contentType("application/json") }
            jsonPath("$.name") { value("Joe") }
            jsonPath("$.contact.email") { value("Joe@Doe.pom") }
        }
        mockMvc.get("/api/v1/humans") {
        }.andExpect {
            status { isOk() }
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Joe") }
            jsonPath("$[0].contact.email") { value("Joe@Doe.pom") }
        }
    }

    @Test
    fun `exo 38`() {
        mockMvc.post("/api/v1/humans") {
            contentType = APPLICATION_JSON
            content = humanJson("Joe")
        }.andExpect {
            status { isCreated() }
            content { contentType("application/json") }
            jsonPath("$.name") { value("Joe") }
            jsonPath("$.contact.email") { value("Joe@Doe.pom") }
            jsonPath("$.pets[0].name") { value("Java") }
        }
        mockMvc.get("/api/v1/humans") {
        }.andExpect {
            status { isOk() }
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Joe") }
            jsonPath("$[0].contact.email") { value("Joe@Doe.pom") }
            jsonPath("$[0].pets[0].name") { value("Java") }
        }
    }

    @Test
    fun `exo 40`() {
        val h = databaseProxy.saveHuman(HumanDto(null, "Joe", ContactDto("Joe@Doe.pom"), listOf(PetDto(null, "Java", 3, PetKind.DOG))))
        mockMvc.delete("/api/v1/humans/${h.humanId}/pets/${h.pets[0].id}") {
        }.andExpect {
            status { isNoContent() }
        }
        mockMvc.get("/api/v1/humans") {
        }.andExpect {
            status { isOk() }
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Joe") }
            jsonPath("$[0].contact.email") { value("Joe@Doe.pom") }
            jsonPath("$[0].pets") { isEmpty() }
        }
    }

    @Test
    fun `exo 41`() {
        val h = databaseProxy.saveHuman(HumanDto(null, "Joe", ContactDto("Joe@Doe.pom"), listOf(PetDto(null, "Java", 3, PetKind.DOG))))
        mockMvc.delete("/api/v1/humans/${h.humanId}/pets/${h.pets[0].id}") {
        }.andExpect {
            status { isUnauthorized() }
        }
        mockMvc.get("/api/v1/humans") {
        }.andExpect {
            status { isOk() }
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Joe") }
            jsonPath("$[0].contact.email") { value("Joe@Doe.pom") }
        }
    }


    @Test
    //@WithMockUser(username = "theAdmin", roles = ["ADMIN"])
    fun `exo 44`() {
        mockMvc.post("/api/v1/humans") {
            contentType = APPLICATION_JSON
            content = humanJson("Joe")
        }.andExpect {
            status { isCreated() }
            content { contentType("application/json") }
            jsonPath("$.name") { value("Joe") }
            jsonPath("$.contact.email") { value("Joe@Doe.pom") }
            jsonPath("$.pets[0].name") { value("Java") }
        }
        mockMvc.get("/api/v1/humans") {
        }.andExpect {
            status { isOk() }
            content { contentType("application/json") }
            jsonPath("$[0].name") { value("Joe") }
            jsonPath("$[0].contact.email") { value("Joe@Doe.pom") }
            jsonPath("$[0].pets[0].name") { value("Java") }
            jsonPath("$[0].creatorLogin") { value("theAdmin") }
        }
    }

    private fun humanJson(name: String = "Joe", email: String = "Joe@Doe.pom", petName: String = "Java") = """
        {
            "name": "$name",
            "contact": {
                "email": "$email"
            },
            "pets": [
                {
                    "name": "$petName",
                    "age": 3,
                    "kind": "DOG"
                }
            ]
        }
    """.trimIndent()
}