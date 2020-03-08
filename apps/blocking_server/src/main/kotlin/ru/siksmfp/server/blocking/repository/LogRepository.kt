package ru.siksmfp.server.blocking.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.siksmfp.server.blocking.model.Log


@Repository
class LogRepository(
        @Autowired
        private val jdbcTemplate: JdbcTemplate
) {

    fun save(log: Log): Long {
        return jdbcTemplate.execute("INSERT INTO log VALUES (?,?,?)") { ps ->
            ps.setString(1, log.client)
            ps.setString(2, log.operation)
            ps.setLong(3, log.entityId)

            val keys = ps.generatedKeys
            return@execute keys.getLong(1)
        }!!
    }
}