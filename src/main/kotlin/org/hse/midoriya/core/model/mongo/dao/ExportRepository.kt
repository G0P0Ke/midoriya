package org.hse.midoriya.core.model.mongo.dao

import org.hse.midoriya.core.model.mongo.Export
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ExportRepository : MongoRepository<Export, String> {

    fun getAllByRussiaAndReporterCodeAndProductCode(russia: Int, reporterCode: Int, cmdCode: String): List<Export>

    fun findExportsByRussiaAndReporterCodeInAndProductCode(russia: Int, reporterCodes: List<Int>, cmdCode: String): List<Export>

    fun findExportsByRussiaAndProductCode(russia: Int, cmdCode: String): List<Export>
}