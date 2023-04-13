package org.hse.midoriya.core.service.domain

import org.hse.midoriya.core.model.jpa.ProductCode
import org.hse.midoriya.core.model.mongo.Export
import org.hse.midoriya.core.model.mongo.Import
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class Converter {

    companion object {
        private val MILLION = BigDecimal.valueOf(1000000)
    }

    fun importToPoint(import: Import) = Point(
        x = import.year.toInt(),
        y = import.primaryValue.toBigDecimal() / MILLION
    )

    fun importsToPoint(imports: Map.Entry<String, List<Import>>): Point {
        val x = imports.key.toInt()
        val y = imports.value.sumOf { it.primaryValue.toBigDecimal() } / MILLION
        return Point(x, y)
    }

    fun exportToPoint(export: Export) = Point(
        x = export.year.toInt(),
        y = export.primaryValue.toBigDecimal() / MILLION
    )

    fun exportsToPoint(exports: Map.Entry<String, List<Export>>): Point {
        val x = exports.key.toInt()
        val y = exports.value.sumOf { it.primaryValue.toBigDecimal() } / MILLION
        return Point(x, y)
    }

    fun productCodesToItems(productCodeList: List<ProductCode>): List<ProductCodeItem> {
        return productCodeList.map { productCode ->
            val exist = if (productCode.exist == null) {
                "Получаем данные по коду"
            } else {
                if (productCode.exist == true) {
                    "В API присутствует информация по такому HS коду"
                } else {
                    "Информация по такому HS коду отсутствует"
                }
            }
            val description = productCode.title!!
            ProductCodeItem(
                code = productCode.code!!,
                description = description,
                exist = exist
            )
        }
    }

}