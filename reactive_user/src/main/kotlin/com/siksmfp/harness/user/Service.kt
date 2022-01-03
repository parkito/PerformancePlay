package com.siksmfp.harness.user

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveService(
    private val repository: ReactiveRepository, private val mapper: Mapper
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun findUserById(id: String): Mono<UserDao> {
        logger.info("find by {}", id)
        return repository.findById(id)
            .map(mapper::toUserDto)
            .switchIfEmpty(
                Mono.error(
                    RuntimeException("User with id $id not found")
                )
            )
    }

    fun deleteUserById(id: String): Mono<Void> {
        logger.info("delete by {}", id)
        return repository.deleteById(id)
            .flatMap {
                Mono.error(
                    RuntimeException("User with id $id was not deleted")
                )
            }
    }

    fun saveUser(userDao: UserDao): Mono<UserDao> {
        logger.info("saving {}", userDao)
        return repository.save(mapper.toUserDoc(userDao))
            .map(mapper::toUserDto)
            .switchIfEmpty(
                Mono.error(
                    RuntimeException("User with id ${userDao.id} was not saved")
                )
            )
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