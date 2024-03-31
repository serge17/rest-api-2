package org.company

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableNeo4jRepositories
class SpringApplication

fun main(args: Array<String>) {
    runApplication<SpringApplication>(*args)
}
