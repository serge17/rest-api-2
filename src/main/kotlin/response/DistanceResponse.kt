package org.company.response

data class DistanceResponse(
    val distance: Int,
    val error: Boolean,
    val message: String
)