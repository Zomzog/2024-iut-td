package iut.nantes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyLittleApplication

fun main(args: Array<String>) {
    runApplication<MyLittleApplication>(*args)
}
