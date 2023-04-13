package org.hse.midoriya.input.rabbit

import org.hse.midoriya.core.enum.Queue.PARSING_RESULT_QUEUE
import org.hse.midoriya.core.service.ProductCodeService
import org.hse.midoriya.core.util.JsonUtilService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class ParsingResultReceiver(
    private val jsonUtilService: JsonUtilService,
    private val productCodeService: ProductCodeService
) {

    companion object {
        val log = LoggerFactory.getLogger(ParsingResultReceiver::class.java)
    }

    @RabbitListener(queues = [PARSING_RESULT_QUEUE])
    fun receiveParsingResultInput(message: Message<String>) {
        val receiveMessage = jsonUtilService.extractMessage(message, ParsingResultMessage::class.java)
        log.info("Receive message $receiveMessage")
        if (receiveMessage == null) {
            log.error("Message from $PARSING_RESULT_QUEUE can not be extracted")
        } else {
            productCodeService.processingProductCodeParsingResult(receiveMessage)
        }
    }

}