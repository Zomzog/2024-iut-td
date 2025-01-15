package iut.nantes

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class AppConfig {
    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun listDatabase() = ListDatabase()

    @Bean
    fun userService() = UserService(listDatabase())

    @Bean
    fun superUserService() = SuperUserService(listDatabase())
}