package iut.nantes

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean("theHashDb")
    fun  hashDb() = HashDatabase()

    @Bean
    fun userService(database: Database) = UserService(database)

    @Bean
    fun superUserService() = SuperUserService()
}