package ru.siksmfp.server.blocking.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.siksmfp.server.blocking.model.User
import ru.siksmfp.server.blocking.repository.UserRepository
import ru.siksmfp.server.blocking.service.Operation.SAVE_USER

@Service
class UserService(
        @Autowired
        private val logService: LogService,

        @Autowired
        private val userRepository: UserRepository
) {

    @Transactional
    fun save(user: User): Long {
        user.id = null
        val id = userRepository.save(user)
        logService.log("${user.name} ${user.id}", SAVE_USER, id)
        return id
    }

    fun find(id: Long): User {
        return userRepository.find(id) ?: throw IllegalStateException("Not fond")
    }
}