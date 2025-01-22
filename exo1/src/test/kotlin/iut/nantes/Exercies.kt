package iut.nantes

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import java.util.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class Exercies {

    @Test
    fun exo1_1() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService: UserService = context.getBean(UserService::class.java)
        userService.save(user())
        val user = userService.findOne(user().id)

        assertThat(user).isEqualTo(user())
    }

    @Test
    fun exo1_2() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService: UserService = context.getBean(UserService::class.java)
        val superUserService: SuperUserService = context.getBean(SuperUserService::class.java)
        userService.save(user())

        assertThat(superUserService.findAll()).isEqualTo(listOf(user()))
    }

    @Test
    fun exo1_3() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService: UserService = context.getBean(UserService::class.java)
        val superUserService: SuperUserService = context.getBean(SuperUserService::class.java)
        userService.save(user())

        assertThat(superUserService.findAll()).isEmpty()
    }

    @Test
    fun exo1_7() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService = context.getBean(UserService::class.java)
        val superUserService = context.getBean(SuperUserService::class.java)

        assertThat(userService.database).isInstanceOf(ListDatabase::class)
        assertThat(superUserService.database).isInstanceOf(HashDatabase::class)
    }
}

fun user(uuid: UUID = UUID(0, 1), name: String = "John Doe") = User(uuid, name, "email@noop.pony", 42)

@SpringBootTest
class Exo8 {

    @Autowired
    private lateinit var userService: UserService
    @MockkBean
    private lateinit var database: Database

    @Test
    fun exo_9() {
        // GIVEN
        every { database.delete(any()) } returns Unit
        every { database.delete(user()) } throws NoSuchElementException()

        // THEN
        assertThrows<NoSuchElementException> { userService.delete(user()) }
        userService.delete(user(UUID.randomUUID()))
    }

    @Test
    fun exo_8() {
        // Test le chargement du contexte
    }
}
