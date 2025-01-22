package iut.nantes.exo45.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(ClientProperties::class)
class HttpClientConfig(val clientProperties: ClientProperties) {
    @Bean
    @Profile("restclient")
    fun restClient(builder: RestClient.Builder): RestClient = builder
        .baseUrl(clientProperties.baseUrl)
        .defaultHeaders { it["Pony"] = "Yolo" }
        .requestInitializer{ it.headers["X-THREAD"]= Thread.currentThread().name }
        .build()

    @Bean
    @Profile("webclient")
    fun webClient(builder: WebClient.Builder): WebClient = builder
        .baseUrl(clientProperties.baseUrl)
        .defaultHeaders { it["Pony"] = "Yolo" }
        .filter { req, next ->
            val updated = ClientRequest.from(req)
                .header("X-THREAD", Thread.currentThread().name)
                .build()
            next.exchange(updated)
        }
        .build()
}