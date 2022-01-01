package com.siksmfp.harness.user

import org.springframework.stereotype.Service

@Service
class ReactiveService(
    private val repository: ReactiveRepository
) {

    fun findUserById(id: Long): UserDao {
        val retrived = repository.findById("")
    }
}