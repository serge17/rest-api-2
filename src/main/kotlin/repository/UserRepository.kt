package org.company.repository

import org.company.model.User
import org.neo4j.driver.internal.value.PathValue
import org.springframework.data.neo4j.repository.Neo4jRepository
import reactor.core.publisher.Flux


interface UserRepository: Neo4jRepository<User, Long> {
    fun findByName(name: String): User?
    fun findByFriendsName(name: String): List<User>
    fun shortestPath(from: User, to: User): Flux<PathValue?>?
}
