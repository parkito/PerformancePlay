package ru.siksmfp.server.blocking.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import ru.siksmfp.server.blocking.model.Message
import java.sql.Connection
import java.sql.ResultSet

@Repository
class MessageRepository(
        @Autowired
        private val jdbcTemplate: JdbcTemplate
) : GenericRepository<Message>() {

    companion object {
        private const val INSERT_SQL = "INSERT INTO MESSAGE(\"from\", \"to\", message) VALUES (?,?,?)"
    }

    private class LogMapper : RowMapper<Message> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Message? {
            return Message(
                    id = rs.getLong("id"),
                    from = rs.getLong("from"),
                    to = rs.getLong("to"),
                    message = rs.getString("message")
            )
        }
    }

    override fun save(t: Message): Long {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection: Connection ->
            val ps = connection
                    .prepareStatement(INSERT_SQL, arrayOf("id"))
            ps.setLong(1, t.from)
            ps.setLong(2, t.to)
            ps.setString(3, t.message)
            ps
        }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    override fun getTableName(): String {
        return "MESSAGE"
    }

    override fun jdbcTemplate(): JdbcTemplate {
        return jdbcTemplate
    }

    override fun getMapper(): RowMapper<Message> {
        return LogMapper()
    }
}