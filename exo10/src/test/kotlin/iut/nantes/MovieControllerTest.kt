package iut.nantes

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/**
 *
 *
 *
 * FOR EXO 19
 *
 *
 *
 *
 */
@AutoConfigureMockMvc
@SpringBootTest
class MovieControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun demoGet() {
        mockMvc.perform(post("/api/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""{"name": "The Dark Knight", "year": 2008}"""))
            .andExpect {
                status { isBadRequest() }
            }
       mockMvc.get("/api/movies", "1") {
                param("id", "1")
           param("toto", "titi")
       }
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$[0].name") { value("The Dark Knight") }
            }
    }

    @Test
    fun demoPost() {
       mockMvc.post("/api/movies") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "The Dark Knight", "year": 2008}"""
        }.andExpect {
            status { isBadRequest() }
        }
    }
}