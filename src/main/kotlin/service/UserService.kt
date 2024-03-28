package org.company.service

import org.company.Util
import org.company.exception.UserNotFoundException
import org.company.repository.FriendsRepository
import org.company.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val friendsRepository: FriendsRepository
) {

    fun findDistance(firstUserId: Long, secondUserId: Long) : Int{
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