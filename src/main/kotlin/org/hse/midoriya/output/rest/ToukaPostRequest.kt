package org.hse.midoriya.output.rest

data class ToukaPostRequest(
    val flowCode: List<String>,
    val cmdCode: String,
    val period: List<String>
)