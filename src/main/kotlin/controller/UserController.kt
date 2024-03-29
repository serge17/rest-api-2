package org.company.controller

import org.company.Util
import org.company.model.User
import org.company.response.ConnectUsersResponse
import org.company.response.DistanceResponse
import org.company.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class UserController(
    @Autowired
    private val userService: UserService
) {

    @GetMapping("/users/{id}")
    fun one(@PathVariable id: Long): User {
        return userService.getById(id)
    }

    @GetMapping("/users")
    fun all(): List<User> {
        return userService.getAll()
    }

    @PostMapping("/users")
    fun newUser(@RequestBody user: User): User {
        return userService.saveUser(user)
    }


    @PutMapping("/users/{id}")
    fun replaceUser(@RequestBody newUser: User, @PathVariable id: Long): User {
        return userService.replaceUser(newUser, id)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }

    @GetMapping("/users/{first}/distance/{second}")
    fun findDistance(@PathVariable first: Long, @PathVariable second: Long): DistanceResponse {
        val dist = userService.findDistance(first, second)
        if (dist == 0) {
            return DistanceResponse(dist, true, "Users are not friends or their distance is greater than ${Util.maxDist}")
        }
        return DistanceResponse(dist, false, "")
    }

    @PostMapping("/users/{first}/connect/{second}")
    fun connectUsers(@PathVariable first: Long, @PathVariable second: Long): ConnectUsersResponse {
        return userService.connectUsers(first, second)
    }
}