package iut.nantes.exo45.client

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@Profile("webclient")
class WebClientService(val webClient: WebClient) {
    fun hello(): HelloDto? {
        TODO()
    }

    fun noHello(): HelloDto? {
        TODO()
    }

    fun error() {
        TODO()
    }
}