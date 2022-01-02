package com.siksmfp.harness.user

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveService(
    private val repository: ReactiveRepository, private val mapper: Mapper
) {

    fun findUserById(id: String): Mono<UserDao> {
        return repository.findById(id).map(mapper::toUserDto).switchIfEmpty(
            Mono.error(
                RuntimeException("User with id $id not found")
            )
        )
    }

    fun saveUser(userDao: UserDao): Mono<UserDao> {
        return repository.save(mapper.toUserDoc(userDao)).map(mapper::toUserDto).switchIfEmpty(
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