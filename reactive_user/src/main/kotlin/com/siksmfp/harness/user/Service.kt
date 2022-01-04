package com.siksmfp.harness.user

import org.slf4j.LoggerFactory.getLogger
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReactiveService(
    private val repository: ReactiveRepository, private val mapper: Mapper
) {

    companion object {
        @JvmStatic
        private val logger = getLogger(this::class.java)
    }

    fun findById(id: String): Mono<UserDao> {
        logger.info("find by {}", id)
        return repository.findById(id)
            .map(mapper::toUserDto)
    }

    fun delete(id: String): Mono<Void> {
        logger.info("delete by {}", id)
        return repository.deleteById(id)
    }

    fun save(userDao: UserDao): Mono<UserDao> {
        logger.info("saving {}", userDao)
        return repository.save(mapper.toUserDoc(userDao))
            .map(mapper::toUserDto)
    }

    fun findAll(page: PageRequest): Flux<UserDao> {
        return repository.findAllBy(page)
            .map(mapper::toUserDto)
    }

    fun count(): Mono<Long> {
        return repository.count()
    }
}

@Component
class Mapper {

    fun toUserDoc(userDao: UserDao): UserMongo {
        return UserMongo(
            username = userDao.username,
            password = userDao.password,
            age = userDao.age
        )
    }

    fun toUserDto(userMongo: UserMongo): UserDao {
        return UserDao(
            id = userMongo.id ?: "",
            username = userMongo.username,
            password = userMongo.password,
            age = userMongo.age
        )
    }
}