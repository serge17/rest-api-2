package org.company.controller

import org.company.Util
import org.company.exception.UserNotFoundException
import org.company.model.User
import org.company.repository.UserRepository
import org.company.response.DistanceResponse
import org.company.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class UserController(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val userService: UserService
) {

    @GetMapping("/users/{id}")
    fun one(@PathVariable id: Long): User {
        val userOptional = userRepository.findById(id)
        if (userOptional.isEmpty) {
            throw UserNotFoundException(id)
        }
        return userOptional.get()
    }

    @GetMapping("/users")
    fun all(): List<User> {
        return userRepository.findAll()
    }

    @PostMapping("/users")
    fun newUser(@RequestBody user: User): User {
        return userRepository.save(user)
    }


    @PutMapping("/users/{id}")
    fun replaceUser(@RequestBody newUser: User, @PathVariable id: Long): User {
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

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userRepository.deleteById(id)
    }

    @GetMapping("/users/{first}/distance/{second}")
    fun findDistance(@PathVariable first: Long, @PathVariable second: Long): DistanceResponse {
        val dist = userService.findDistance(first, second)
        if (dist == 0) {
            return DistanceResponse(dist, true, "Users are not friends or their distance is greater than ${Util.maxDist}")
        }
        return DistanceResponse(dist, false, "")
    }
}