package org.hse.midoriya.core.model.mongo.dao

import org.hse.midoriya.core.model.mongo.Import
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ImportRepository : MongoRepository<Import, String> {
    fun getAllByRussiaAndReporterCodeAndProductCode(russia: Int, reporterCode: Int, cmdCode: String): List<Import>

    fun findImportsByRussiaAndReporterCodeInAndProductCode(russia: Int, codeList: List<Int>, cmdCode: String): List<Import>
}