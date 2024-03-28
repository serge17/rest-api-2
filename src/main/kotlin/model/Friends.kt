package org.company.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity
data class Friends  (
    val first: Long,
    val second: Long,
    @Id
    @GeneratedValue
    val id: Long? = null,
    val createdAtUtc: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),
    val updatedAtUtc: OffsetDateTime? = OffsetDateTime.now(ZoneOffset.UTC),
)