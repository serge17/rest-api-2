package org.company.config

import org.company.model.User
import org.company.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class LoadDatabase {
    private val log: Logger = LoggerFactory.getLogger(LoadDatabase::class.java)

    @Bean
    fun initDatabase(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            userRepository.deleteAll()

            val user1 = userRepository.save(User("John Johnson", "john.johnson@example.com", "12345678"))
            val user2 = userRepository.save(User("Steve Stephenson", "steve.stephenson@example.com", "987654321"))
            val user3 = userRepository.save(User("Jack Jackson", "jack.jackson@example.com", "0908978874233"))
            val user4 = userRepository.save(User("Pete Peterson", "pete.peterson@example.com", "12345678"))
            val user5 = userRepository.save(User("Charles Carlson", "charles.carlson@example.com", "09098776123"))
            val user6 = userRepository.save(User("Bob Robertson", "bob.robertson@example.com", "7676321123"))
            val user7 = userRepository.save(User("Brian Brianson", "brian.brianson@example.com", "87423784623746"))


            user1.friends.add(user2)
            user2.friends.add(user3)
            user3.friends.add(user4)
            user4.friends.add(user5)
            user5.friends.add(user6)

            userRepository.save(user1)
            userRepository.save(user2)
            userRepository.save(user3)
            userRepository.save(user4)
            userRepository.save(user5)

            log.info("Preloading $user1")
            log.info("Preloading $user2")
            log.info("Preloading $user3")
            log.info("Preloading $user4")
            log.info("Preloading $user5")
            log.info("Preloading $user6")
            log.info("Preloading $user7")

            val path = userRepository.shortestPath(user1, user3)
            log.info("Path: $path")
        }
    }
}