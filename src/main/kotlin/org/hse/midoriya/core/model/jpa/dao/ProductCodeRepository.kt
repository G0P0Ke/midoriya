package org.hse.midoriya.core.model.jpa.dao

import org.hse.midoriya.core.model.jpa.ProductCode
import org.springframework.data.jpa.repository.JpaRepository


interface ProductCodeRepository : JpaRepository<ProductCode, String> {

    fun getProductCodeByIsActive(isActive: Boolean): List<ProductCode>

    override fun findAll(): List<ProductCode>

    fun getProductCodeByCode(code: String): ProductCode?
}