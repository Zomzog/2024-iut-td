package iut.nantes

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
@ComponentScan("iut.nantes")
class AppConfig {
    @Bean("theHashDb")
    fun  hashDb() = HashDatabase()

    @Bean
    fun userService(database: Database) = UserService(database)

    @Bean
    fun superUserService() = SuperUserService()
}