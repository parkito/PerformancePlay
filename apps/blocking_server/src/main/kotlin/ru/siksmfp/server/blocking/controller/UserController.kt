package ru.siksmfp.server.blocking.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.siksmfp.server.blocking.model.User
import ru.siksmfp.server.blocking.service.UserService

@RestController
@RequestMapping("/api/v1/user")
class UserController(
        @Autowired
        private val userService: UserService
) {
    @PostMapping
    fun save(@RequestBody user: User): ResponseEntity<Long> {
        return ResponseEntity.ok(userService.save(user))
    }

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long): ResponseEntity<User> {
        return ResponseEntity.ok(userService.find(id))
    }
}