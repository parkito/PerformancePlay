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
) : GenericRepository<Log>() {

    companion object {
        private const val INSERT_SQL = "INSERT INTO LOG(client, operation, entityId) VALUES (?,?,?)"
    }

    private class LogMapper : RowMapper<Log> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Log? {
            return Log(
                    id = rs.getLong("id"),
                    client = rs.getString("client"),
                    operation = rs.getString("operation"),
                    entityId = rs.getLong("entityId")
            )
        }
    }

    override fun save(t: Log): Long {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection: Connection ->
            val ps = connection
                    .prepareStatement(INSERT_SQL, arrayOf("id"))
            ps.setString(1, t.client)
            ps.setString(2, t.operation)
            ps.setLong(3, t.entityId)
            ps
        }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    override fun getTableName(): String {
        return "LOG"
    }

    override fun jdbcTemplate(): JdbcTemplate {
        return jdbcTemplate
    }

    override fun getMapper(): RowMapper<Log> {
        return LogMapper()
    }
}