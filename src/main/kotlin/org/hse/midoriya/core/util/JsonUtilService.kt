package org.hse.midoriya.core.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class JsonUtilService {

    fun <T> extractMessage(message: Message<String>, clazz: Class<T>) = extractMessage(message.payload, clazz)

    fun <T> extractMessage(message: String, clazz: Class<T>): T? {
        return try {
            val objectMapper = ObjectMapper()
            objectMapper.readValue(message, clazz)
        } catch (e: JsonProcessingException) {
            null
        }
    }
}