package iut.nantes

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun database() = ListDatabase()

    @Bean
    fun userService(@Qualifier("database") db: Database) = UserService(db)

    @Bean
    fun superUserService(@Qualifier("database") db: Database) = SuperUserService(database())
}