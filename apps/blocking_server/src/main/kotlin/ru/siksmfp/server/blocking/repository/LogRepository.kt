package ru.siksmfp.server.blocking.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import ru.siksmfp.server.blocking.model.Log
import java.sql.Connection
import java.sql.ResultSet

@Repository
class LogRepository(
        @Autowired
        private val jdbcTemplate: JdbcTemplate
) {
    companion object {
        private const val INSERT_SQL = "INSERT INTO log(client, operation, entityId) VALUES (?,?,?)"
        private const val COUNT_SQL = "SELECT COUNT(1) FROM log"
        private const val FIND_BY_ID_SQL = "SELECT * FROM log WHERE id = ?"
        private const val FIND_ALL_SQL = "SELECT * FROM log"
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

    fun find(id: Long): Log? {
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, arrayOf(id), LogMapper())
    }

    fun findAll(): List<Log> {
        return jdbcTemplate.query(FIND_ALL_SQL, LogMapper())
    }

    fun count(): Long {
        return jdbcTemplate
                .queryForObject(COUNT_SQL, Long::class.java)!!.toLong()
    }

    class LogMapper : RowMapper<Log> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Log? {
            return Log(
                    id = rs.getLong("id"),
                    client = rs.getString("client"),
                    operation = rs.getString("operation"),
                    entityId = rs.getLong("entityId")
            )
        }
    }
}