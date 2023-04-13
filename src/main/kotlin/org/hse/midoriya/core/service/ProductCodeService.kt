package org.hse.midoriya.core.service

import org.hse.midoriya.core.model.jpa.ProductCode
import org.hse.midoriya.core.model.jpa.dao.ProductCodeRepository
import org.hse.midoriya.core.service.domain.ProductCodeForm
import org.hse.midoriya.input.rabbit.ParsingResultMessage
import org.hse.midoriya.output.rest.ToukaClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductCodeService(
    private val productCodeRepository: ProductCodeRepository,
    private val toukaClient: ToukaClient
) {

    companion object {
        private val log = LoggerFactory.getLogger(ProductCodeService::class.java)
        private const val FIRST_YEAR = 2018
        private val FLOW_CODE = listOf("M")
    }

    fun getActiveProductCode(): List<ProductCode> {
        return productCodeRepository.getProductCodeByIsActive(true)
    }

    fun getAllProductCode(): List<ProductCode> {
        return productCodeRepository.findAll()
    }

    @Transactional
    fun askForNewDataForProductCode(data: ProductCodeForm) {
        val productCode = productCodeRepository.getProductCodeByCode(data.code)
        var exist = false
        if (productCode != null) {
            log.info("ProductCode ${data.code} already exist")
            exist = true
        }
        if (!exist) {
            val newProductCode = ProductCode(
                code = data.code,
                title = data.title
            )
            productCodeRepository.save(newProductCode)
        }
        val now = Calendar.getInstance().get(Calendar.YEAR)
        val periodList = mutableListOf<String>()
        for(year in FIRST_YEAR..now) {
            periodList.add(year.toString())
        }
        toukaClient.askForNewData(data.code, periodList, FLOW_CODE)
    }

    @Transactional
    fun processingProductCodeParsingResult(message: ParsingResultMessage) {
        val productCode = productCodeRepository.getProductCodeByCode(message.cmdCode)
        if (productCode == null) {
            log.error("Product with ${message.cmdCode} doesn't exist")
            return
        }
        if (!productCode.isActive) {
            productCode.exist = message.exist
            productCode.isActive = message.exist
        }
        productCodeRepository.save(productCode)
        log.info("Changed Product with code=${message.cmdCode}: isActive = ${message.exist}, " +
            "exist = ${message.exist}")
    }
}