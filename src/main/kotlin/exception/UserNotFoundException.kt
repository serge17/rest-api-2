package org.company.exception

class UserNotFoundException(userId: Long) : RuntimeException("Could not find user: $userId")