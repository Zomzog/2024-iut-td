package iut.nantes.exo45.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("custom.client")
data class ClientProperties (val baseUrl: String)