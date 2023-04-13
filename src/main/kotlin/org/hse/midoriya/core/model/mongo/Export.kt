package org.hse.midoriya.core.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

/***
 * Класс для изучения русского импорта.
 * reporterCode - это код страны, которая огласила цену продукции, которую эскпортировала в Россию
 */
@Document(collection = "export")
data class Export(
    @Id
    val id: String,
    @Field("cmdCode")
    val productCode: String,
    val partner2Code: Int,
    @Field("partnerCode")
    val russia: Int,
    @Field("period")
    val year: String,
    val primaryValue: Double,
    @Field("reporterCode")
    val reporterCode: Int?
)