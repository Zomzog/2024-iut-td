package iut.nantes

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("iut.nantes")
class AppConfig {
    @Bean
    fun userService(database: Database) = UserService(database)

    @Bean
    fun superUserService() = SuperUserService()
}