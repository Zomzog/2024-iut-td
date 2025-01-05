package iut.nantes.exo33.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class FilterConfig {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun filterA(ponyFilter: PonyFilter): FilterRegistrationBean<PonyFilter> {
        val registrationBean = FilterRegistrationBean(ponyFilter)
        registrationBean.addUrlPatterns("/ponies/*")
        registrationBean.order = 1
        return registrationBean
    }

    @Bean
    fun filterB(ponyFilter: PonyHttpFilter): FilterRegistrationBean<PonyHttpFilter> {
        val registrationBean = FilterRegistrationBean<PonyHttpFilter>()
        registrationBean.filter = ponyFilter
        registrationBean.addUrlPatterns("/ponies/*")
        registrationBean.order = 1
        return registrationBean
    }

    @Bean
    fun userDetailService(passwordEncoder: PasswordEncoder): UserDetailsManager {
        val user1 = User.withUsername("u1")
            .password(passwordEncoder.encode("pw"))
            .roles("USER")
            .build()
        val user2 = User.withUsername("user2")
            .password(passwordEncoder.encode("user2Pass"))
            .roles("ADMIN")
            .build()
        val demo = User.withUsername("login")
            .password(passwordEncoder.encode("password"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(user1, user2, demo)
    }
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/ponies", permitAll)
                authorize("/admin", hasRole("ADMIN"))
                authorize(anyRequest, authenticated)
            }
            httpBasic {}
            formLogin {}
        }
        return http.build()
    }
    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://example.com")
        configuration.allowedMethods = listOf("GET", "POST")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}