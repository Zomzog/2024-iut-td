package iut.nantes.exo20.config

import iut.nantes.exo20.service.MovieService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AppConfig {

//    @Bean
//    @Profile("mongo")
//    fun pony(): Pony {
//        println("mongo")
//        return Pony()
//    }
//    @Bean
//    @Profile("!mongo")
//    fun ponyNon(): Pony {
//        println("!mongo")
//        return Pony()
//    }V

    @Bean
    fun movieService(): MovieService {
        return MovieService()
    }
}

//class Pony() {
//    @Value("\${custom.path.value}")
//    private lateinit var value: String
//}