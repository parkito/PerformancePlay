package ru.siksmfp.server.blocking.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.siksmfp.server.blocking.model.Message
import ru.siksmfp.server.blocking.repository.MessageRepository
import ru.siksmfp.server.blocking.repository.UserRepository
import ru.siksmfp.server.blocking.service.Operation.SAVE_MESSAGE

@Service
class MessageService(
        @Autowired
        private val messageRepository: MessageRepository,

        @Autowired
        private val logService: LogService,

        @Autowired
        private val userRepository: UserRepository
) {

    @Transactional
    fun save(message: Message): Long {
        val id = messageRepository.save(message)
        val user = userRepository.find(message.from) ?: throw IllegalStateException("User ${message.from} not fond")
        logService.log("${user.name} ${user.id}", SAVE_MESSAGE, id)
        return id
    }

    fun findById(id: Long): Message {
        return messageRepository.find(id) ?: throw IllegalStateException("Not fond")
    }

    fun findAll(): List<Message> {
        return messageRepository.findAll()
    }
}