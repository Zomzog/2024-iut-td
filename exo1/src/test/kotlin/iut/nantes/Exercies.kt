package iut.nantes

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
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
