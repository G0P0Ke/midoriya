package org.hse.midoriya.input.rabbit


data class ParsingResultMessage(
    val cmdCode: String = "",
    val exist: Boolean = true,
)