package org.company.service

import org.company.Util
import org.company.exception.UserNotFoundException
import org.company.model.Friends
import org.company.model.User
import org.company.repository.FriendsRepository
import org.company.repository.UserRepository
import org.company.response.ConnectUsersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val friendsRepository: FriendsRepository
) {

    fun findDistance(firstUserId: Long, secondUserId: Long) : Int {
        val firstUserOptional = userRepository.findById(firstUserId)
        val secondUserOptional = userRepository.findById(secondUserId)

        if (firstUserOptional.isEmpty) {
            throw UserNotFoundException(firstUserId)
        }
        if (secondUserOptional.isEmpty) {
            throw UserNotFoundException(secondUserId)
        }

        val dist = bfs(firstUserId, secondUserId)
        return dist
    }

    fun connectUsers(firstUserId: Long, secondUserId: Long): ConnectUsersResponse {
        friendsRepository.save(Friends(firstUserId, secondUserId))
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

    private fun bfs(first: Long, second: Long, maxDist: Int = Util.maxDist): Int {
        var dist = 0

        var set = mutableSetOf(first)
        val examined = mutableSetOf<Long>()

        var tempSet = mutableSetOf<Long>()

        while(set.isNotEmpty() && dist <= maxDist) {
            set.forEach {
                if (it == second) {
                    return dist
                } else {
                    val friendsIds = getFriendsIds(it).filter { !examined.contains(it) }
                    examined.addAll(friendsIds)
                    tempSet.addAll(friendsIds)
                }
            }

            set = tempSet
            tempSet = mutableSetOf()
            dist +=1
        }
        return 0
    }

    private fun getFriendsIds(userId: Long): Set<Long> {
        val set = mutableSetOf<Long>()
        val firstFriendsList = friendsRepository.findByFirstOrSecond(userId, userId)

        firstFriendsList.forEach {
            if (it.first != userId) {
                set.add(it.first)
            } else {
                set.add(it.second)
            }
        }
        return set
    }
}