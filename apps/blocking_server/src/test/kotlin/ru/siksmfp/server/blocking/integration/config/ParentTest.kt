package ru.siksmfp.server.blocking.integration.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import javax.sql.DataSource

@SpringBootTest
//@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@Sql(scripts = ["/sql/reset.sql"], executionPhase = AFTER_TEST_METHOD)
class ParentTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    protected var basicPath = "/api/v1/"

    private var isInitialized = false

    @BeforeEach
    fun beforeEach() {
        if (!isInitialized) {
            val populator = ResourceDatabasePopulator()
            populator.addScript(ClassPathResource("/postgres/init.sql"))
            populator.execute(dataSource)
            isInitialized = true
        }
    }
}