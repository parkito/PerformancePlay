package ru.siksmfp.server.blocking.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

abstract class GenericRepository<T> {

    protected val COUNT_SQL = "SELECT COUNT(1) FROM ${this.getTableName()}"
    protected val FIND_BY_ID_SQL = "SELECT * FROM ${this.getTableName()} WHERE id = ?"
    protected val FIND_ALL_SQL = "SELECT * FROM ${this.getTableName()}"

    abstract fun getTableName(): String

    abstract fun jdbcTemplate(): JdbcTemplate

    abstract fun getMapper(): RowMapper<T>

    abstract fun save(t: T): Long

    fun find(id: Long): T? {
        return jdbcTemplate().queryForObject(FIND_BY_ID_SQL, arrayOf(id), getMapper())
    }

    fun findAll(): List<T> {
        return jdbcTemplate().query(FIND_ALL_SQL, getMapper())
    }

    fun count(): Long {
        return jdbcTemplate()
                .queryForObject(COUNT_SQL, Long::class.java)!!.toLong()
    }
}