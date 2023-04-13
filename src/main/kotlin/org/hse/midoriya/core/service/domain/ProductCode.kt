package org.hse.midoriya.core.service.domain


data class ProductCodeForm(
    val code: String = "0",
    val title: String = "",
    val exist: Boolean = false
)

data class ProductCodeItem(
    val code: String = "",
    val description: String = "",
    val exist: String = ""
)