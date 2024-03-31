package org.company.repository

import org.company.model.User
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param

interface UserRepository : Neo4jRepository<User, Long> {
    fun findByName(name: String): User?

    fun findByFriendsName(name: String): List<User>

    @Query("MATCH p = (leaf:User {email: \$email1})-[:FRIEND*]-(:User {email: \$email2}) RETURN p")
    fun findPath(
        @Param("email1") email1: String,
        @Param("email2") email2: String,
    ): List<User>
}
