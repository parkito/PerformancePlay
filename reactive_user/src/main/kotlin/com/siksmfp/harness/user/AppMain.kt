package com.siksmfp.harness.user

import com.siksmfp.harness.user.Router.Companion.API
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class AppMain

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun appClient(webClient: WebClient): AppClient = AppClient(webClient)

    @Bean
    @ConditionalOnMissingBean
    fun webClient(props: AppProperties): WebClient = WebClient.builder()
        .baseUrl("http://${props.host}:${props.port}/$API/")
        .build()
}

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val host: String,
    val port: String
)

fun main(args: Array<String>) {
    runApplication<AppMain>(*args)
}


