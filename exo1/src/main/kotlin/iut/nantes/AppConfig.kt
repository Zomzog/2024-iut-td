package iut.nantes

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun listDatabase() = ListDatabase()

    @Bean
    fun userService() = UserService(listDatabase())
}