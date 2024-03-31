package org.company.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Node
data class User (
    var name: String,
    var email: String,
    var phone: String,
    @Id
    @GeneratedValue
    val id: Long? = null,
    @Relationship(type = "FRIEND")
    val friends: MutableSet<User> = mutableSetOf(),
    val createdAtUtc: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),
    val updatedAtUtc: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),
)
