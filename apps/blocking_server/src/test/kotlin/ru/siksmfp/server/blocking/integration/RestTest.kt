package ru.siksmfp.server.blocking.integration

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
                .content(objectMapper.writeValueAsString(user1))
        ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toLong()

        val responseUserStr = mockMvc.perform(get("/api/v1/user/$userId"))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString

        val responseUser = objectMapper.readValue(responseUserStr, User::class.java)

        Assertions.assertEquals(userId, responseUser.id)
        Assertions.assertEquals(user1.name, responseUser.name)
        Assertions.assertEquals(user1.age, responseUser.age)
    }
}