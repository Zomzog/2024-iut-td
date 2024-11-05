package iut.nantes

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import java.util.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.NoSuchElementException

class Exercies {

    @Test
    fun exo1_1() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService = context.getBean(UserService::class.java)
        userService.save(user())
        val user = userService.findOne(user().id)

        assertThat(user).isEqualTo(user())
    }

    @Test
    fun exo1_2() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService = context.getBean(UserService::class.java)
        val superUserService = context.getBean(SuperUserService::class.java)
        userService.save(user())

        assertThat(superUserService.findAll()).isEqualTo(listOf(user()))
    }

    @Test
    fun exo1_3() {
        val context = AnnotationConfigApplicationContext(AppConfig::class.java)
        val userService = context.getBean(UserService::class.java)
        val superUserService = context.getBean(SuperUserService::class.java)
        userService.save(user())

        assertThat(superUserService.findAll()).isEmpty()
    }


}

@SpringBootTest
class Exo8 {

    @MockkBean
    private lateinit var database: ListDatabase

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun exo_9() {
        // GIVEN TODO
        every { database.delete(any()) } returns Unit
        every { database.delete(user()) } throws NoSuchElementException()

        // THEN
        assertThrows<NoSuchElementException> { userService.delete(user()) }
        userService.delete(user(UUID.randomUUID()))
    }
}

private fun user(uuid: UUID = UUID(0, 1)) = User(uuid, "John Doe", "email@noop.pony", 42)
