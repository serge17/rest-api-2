package org.company.service

import org.company.Util
import org.company.exception.UserNotFoundException
import org.company.model.User
import org.company.repository.UserRepository
import org.company.response.ConnectUsersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
) {

    fun findDistance(firstUserId: Long, secondUserId: Long) : Int {
        return 0
    }

    fun connectUsers(firstUserId: Long, secondUserId: Long): ConnectUsersResponse {

//        friendsRepository.save(Friends(firstUserId, secondUserId))
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

    fun replaceUser(newUser: User, id: Long): User {
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