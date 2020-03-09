package ru.siksmfp.server.blocking.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.siksmfp.server.blocking.model.Message
import ru.siksmfp.server.blocking.service.MessageService

@RestController
@RequestMapping("/api/v1/message")
class MessageController(
        @Autowired
        private val messageService: MessageService
) {

    @PostMapping
    fun save(message: Message): ResponseEntity<Long> {
        return ResponseEntity.ok(messageService.save(message))
    }
}