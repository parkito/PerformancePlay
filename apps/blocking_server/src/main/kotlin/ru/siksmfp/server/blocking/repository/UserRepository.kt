package ru.siksmfp.server.blocking.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import ru.siksmfp.server.blocking.model.User
import java.sql.Connection
import java.sql.ResultSet

@Repository
class UserRepository(
        @Autowired
        private val jdbcTemplate: JdbcTemplate
) : GenericRepository<User>() {

    companion object {
        private const val INSERT_SQL = "INSERT INTO \"user\"(name, age) VALUES (?,?)"
    }

    private class LogMapper : RowMapper<User> {
        override fun mapRow(rs: ResultSet, rowNum: Int): User? {
            return User(
                    id = rs.getLong("id"),
                    name = rs.getString("name"),
                    age = rs.getInt("operation")
            )
        }
    }

    override fun save(t: User): Long {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection: Connection ->
            val ps = connection
                    .prepareStatement(INSERT_SQL, arrayOf("id"))
            ps.setString(1, t.name)
            ps.setInt(2, t.age)
            ps
        }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    override fun getTableName(): String {
        return "user"
    }

    override fun jdbcTemplate(): JdbcTemplate {
        return jdbcTemplate
    }

    override fun getMapper(): RowMapper<User> {
        return LogMapper()
    }
}