package ru.siksmfp.server.blocking.repository

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import ru.siksmfp.server.blocking.model.Log
import java.sql.Connection


@Repository
class LogRepository(
        @Autowired
        private val jdbcTemplate: JdbcTemplate
) {
    companion object {
        private var logger = LoggerFactory.getLogger(LogRepository::class.java)
        private const val INSERT_SQL = "INSERT INTO log(client, operation, entityId) VALUES (?,?,?)"
    }

    fun save(log: Log): Long {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection: Connection ->
            val ps = connection
                    .prepareStatement(INSERT_SQL, arrayOf("id"))
            ps.setString(1, log.client)
            ps.setString(2, log.operation)
            ps.setLong(3, log.entityId)
            ps
        }, keyHolder)

        return keyHolder.key!!.toLong()
    }
}