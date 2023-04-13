package org.hse.midoriya.output.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@RefreshScope
class ToukaClient(
    private val toukaRestTemplate: RestTemplate,
    @Value("\${touka.env}/get") private val urlStartParsing: String
) {

    companion object {
        private val log = LoggerFactory.getLogger(ToukaClient::class.java)
    }

    fun askForNewData(cmdCode: String, period: List<String>, flowCode: List<String>) {
        val request = ToukaPostRequest(
            cmdCode = cmdCode,
            period = period,
            flowCode = flowCode
        )
        log.info("Ask $urlStartParsing new parsing for $request")
        try {
            val response = toukaRestTemplate.postForObject(
                urlStartParsing,
                request,
                String::class.java
            )
            log.info("Touka answer $response")
        } catch (e: Exception) {
            log.error(e.message)
        }
    }
}