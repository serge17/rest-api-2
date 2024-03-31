package org.company.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Node
data class User(
    @Property("name")
    var name: String,
    @Property("email")
    var email: String,
    @Property("phone")
    var phone: String,
    @Id
    @GeneratedValue
    val userId: Long? = null,
    @Relationship(type = "FRIEND")
    val friends: MutableSet<User> = mutableSetOf(),
    @Property("createdAtUtc")
    val createdAtUtc: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),
    @Property("updatedAtUtc")
    val updatedAtUtc: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),
)
