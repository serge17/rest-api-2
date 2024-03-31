package org.company.service

import org.company.exception.UserNotFoundException
import org.company.model.User
import org.company.repository.UserRepository
import org.company.response.ConnectUsersResponse
import org.company.response.DistanceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
) {
    fun findDistance(
        firstUserId: Long,
        secondUserId: Long,
    ): DistanceResponse {
        val firstUserOptional = userRepository.findById(firstUserId)
        val secondUserOptional = userRepository.findById(secondUserId)
        if (firstUserOptional.isEmpty) {
            return DistanceResponse(0, true, "User with id $firstUserId not found.")
        }
        if (secondUserOptional.isEmpty) {
            return DistanceResponse(0, true, "User with id $secondUserOptional not found.")
        }
        val path = userRepository.findPath(firstUserOptional.get().email, secondUserOptional.get().email)
        if (path.isEmpty()) {
            return DistanceResponse(0, true, "User $firstUserId and $secondUserId are not connected")
        }
        return DistanceResponse(path.size - 1, false, "")
    }

    fun connectUsers(
        firstUserId: Long,
        secondUserId: Long,
    ): ConnectUsersResponse {
        val firstUserOptional = userRepository.findById(firstUserId)
        val secondUserOptional = userRepository.findById(secondUserId)
        if (firstUserOptional.isEmpty) {
            return ConnectUsersResponse(false, "User with id $firstUserId not found.")
        }
        if (secondUserOptional.isEmpty) {
            return ConnectUsersResponse(false, "User with id $secondUserOptional not found.")
        }

        val firstUser = firstUserOptional.get()
        firstUser.friends.add(secondUserOptional.get())

        userRepository.save(firstUser)
        return ConnectUsersResponse(true, "")
    }

    fun getById(id: Long): User {
        val userOptional = userRepository.findById(id)
        if (userOptional.isEmpty) {
            throw UserNotFoundException(id)
        }
        return userOptional.get()
    }

    fun getAll(): List<User> {
        return userRepository.findAll()
    }

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun replaceUser(
        newUser: User,
        id: Long,
    ): User {
        val userOptional = userRepository.findById(id)

        if (userOptional.isEmpty) {
            val createdUser = User(newUser.name, newUser.email, newUser.phone, id)
            userRepository.save(createdUser)
            return createdUser
        } else {
            val user = userOptional.get()
            user.name = newUser.name
            user.email = newUser.email
            user.phone = newUser.phone
            userRepository.save(user)
            return user
        }
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
