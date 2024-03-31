package org.company.controller

import org.company.model.User
import org.company.response.ConnectUsersResponse
import org.company.response.DistanceResponse
import org.company.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired
    private val userService: UserService,
) {
    @GetMapping("/{id}")
    fun one(
        @PathVariable id: Long,
    ): User {
        return userService.getById(id)
    }

    @GetMapping
    fun all(): List<User> {
        return userService.getAll()
    }

    @PostMapping
    fun newUser(
        @RequestBody user: User,
    ): User {
        return userService.saveUser(user)
    }

    @PutMapping("/{id}")
    fun replaceUser(
        @RequestBody newUser: User,
        @PathVariable id: Long,
    ): User {
        return userService.replaceUser(newUser, id)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable id: Long,
    ) {
        userService.deleteUser(id)
    }

    @GetMapping("/{first}/distance/{second}")
    fun findDistance(
        @PathVariable first: Long,
        @PathVariable second: Long,
    ): DistanceResponse {
        return userService.findDistance(first, second)
    }

    @PostMapping("/{first}/connect/{second}")
    fun connectUsers(
        @PathVariable first: Long,
        @PathVariable second: Long,
    ): ConnectUsersResponse {
        return userService.connectUsers(first, second)
    }
}
