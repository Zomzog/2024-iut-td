package iut.nantes.exo20.controller

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

class InfoTest{

    @Nested
    @WebMvcTest
    inner class Exo28 {

        @Autowired
        lateinit var mockMvc: MockMvc

        @Test
        fun `happy path`() {
            mockMvc.get("/api/v1/info")
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$.appName") { value("mon application") }
                    jsonPath("$.appVersion") { value("1.0.1") }
                    jsonPath("$.git.branch") { value("main") }
                    jsonPath("$.git.commit") { value("123456") }
                }
        }
    }

    @Nested
    @ActiveProfiles("dev")
    @WebMvcTest
    inner class Exo30 {

        @Autowired
        lateinit var mockMvc: MockMvc

        @Test
        fun `happy path`() {
            mockMvc.get("/api/v1/info")
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json") }
                    jsonPath("$.appName") { value("mon application") }
                    jsonPath("$.appVersion") { value("1.0.2-SNAPSHOT") }
                    jsonPath("$.git.branch") { value("feature/TICKET") }
                    jsonPath("$.git.commit") { value("654321") }
                }
        }
    }

}