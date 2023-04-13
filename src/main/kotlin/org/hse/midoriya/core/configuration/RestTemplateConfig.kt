package org.hse.midoriya.core.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    fun toukaRestTemplate(): RestTemplate {
        return RestTemplate()
    }
}