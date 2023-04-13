package org.hse.midoriya.core.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

/***
 * Класс для изучения русского экспорта.
 * reporterCode - это код страны, которая огласила цену продукции, которую импортировала из России
 */
@Document(collection = "import")
data class Import(
    @Id
    val id: String,
    @Field("cmdCode")
    val productCode: String,
    @Field("partner2Code")
    val secondPartner: Int,
    @Field("partnerCode")
    val russia: Int,
    @Field("period")
    val year: String,
    val primaryValue: Double,
    val reporterCode: Int?
)