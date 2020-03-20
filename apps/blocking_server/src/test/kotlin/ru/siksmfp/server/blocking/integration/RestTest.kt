package ru.siksmfp.server.blocking.integration

import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.siksmfp.server.blocking.integration.config.ParentTest
import ru.siksmfp.server.blocking.model.User

class RestTest : ParentTest() {

    @Test
    fun test() {
        val user1 = User(null, "name1", 1)
        val userId = mockMvc.perform(post("/api/v1/user/")
                .contentType("application/json")
                .param("sendWelcomeMail", "true")
                .content(objectMapper.writeValueAsString(user1))
        ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toLong()
    }
}