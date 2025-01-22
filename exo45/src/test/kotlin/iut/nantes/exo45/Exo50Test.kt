package iut.nantes.exo45

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.databind.ObjectMapper
import iut.nantes.exo45.client.HelloDto
import iut.nantes.exo45.client.MyInternalError
import iut.nantes.exo45.client.RestClientService
import iut.nantes.exo45.client.WebClientService
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("restclient")
class Exo50Test {

    private val mockServer = startClientAndServer(8888)

    @Autowired
    lateinit var service: RestClientService

    @BeforeEach
    fun reset() {
        mockServer.reset()
    }

    @AfterAll
    fun stopServer() {
        mockServer.stop()
    }

    @WithMockUser
    @Test
    fun exo50() {
        // GIVEN
        mockServer.`when`(
            request()
                .withMethod("GET")
                .withPath("/api/v1/hello")
        ).respond(
            successResponse()
        )
        // WHEN
        val hello = service.hello()

        // THEN
        assertThat(hello).isEqualTo(HelloDto("Hello, World!"))
    }

    @WithMockUser
    @Test
    fun exo51() {
        // GIVEN
        mockServer.`when`(
            request()
                .withMethod("GET")
                .withPath("/api/v1/hello")
                .withQueryStringParameter("name", "World")
        ).respond(
            successResponse()
        )
        // WHEN
        val hello = service.hello()

        // THEN
        assertThat(hello).isEqualTo(HelloDto("Hello, World!"))
    }

    @WithMockUser
    @Test
    fun exo52() {
        // GIVEN
        mockServer.`when`(
            request()
                .withMethod("POST")
                .withPath("/api/v1/noHello")
                .withBody(ObjectMapper().writeValueAsString(HelloDto("No hello")))
        ).respond(
            successResponse("No hello...")
        )
        // WHEN
        val hello = service.noHello()

        // THEN
        assertThat(hello).isEqualTo(HelloDto("No hello..."))
    }

    @WithMockUser
    @Test
    fun exo53() {
        // GIVEN
        mockServer.`when`(
            request()
                .withMethod("GET")
                .withPath("/api/v1/error")
        ).respond(
            HttpResponse.response()
                .withStatusCode(500)
        )
        // WHEN
        assertThrows(MyInternalError::class.java) { service.error()}

    }

    private fun successResponse(message: String = """Hello, World!"""): HttpResponse? = HttpResponse.response()
        .withStatusCode(200)
        .withHeader("Content-Type", "application/json")
        .withBody(
            """
                        {
                            "message": "$message"
                        }
                    """.trimIndent()
        )
}