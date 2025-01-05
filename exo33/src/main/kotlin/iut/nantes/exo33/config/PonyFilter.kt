package iut.nantes.exo33.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlin.math.log

@Component
class PonyFilter : Filter {
    private val logger = KotlinLogging.logger {}
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        logger.debug { "Protocol: ${request.protocol}" }
        chain.doFilter(request, response)
    }
}

@Component
class PonyHttpFilter : HttpFilter() {
    private val logger = KotlinLogging.logger {}
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        logger.debug { "Logging request 2 ${request.method} ${request.requestURI}" }
        chain.doFilter(request, response)
        logger.debug { "Logging response 2 ${response.status}" }
    }
}
