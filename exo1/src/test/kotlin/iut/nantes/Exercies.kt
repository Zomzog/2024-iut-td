package iut.nantes

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import java.util.*
import org.junit.jupiter.api.Test
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
        val userService: UserService = TODO()
        val superUserService: SuperUserService = TODO()
        userService.save(user())

        assertThat(superUserService.findAll()).isEqualTo(listOf(user()))
    }

    @Test
    fun exo1_3() {
        val userService: UserService = TODO()
        val superUserService: SuperUserService = TODO()
        userService.save(user())

        assertThat(superUserService.findAll()).isEmpty()
    }


}

private fun user(uuid: UUID = UUID(0, 1)) = User(uuid, "John Doe", "email@noop.pony", 42)
