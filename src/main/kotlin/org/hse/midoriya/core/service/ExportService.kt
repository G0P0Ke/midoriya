package org.hse.midoriya.core.service

import org.hse.midoriya.core.enum.Country
import org.hse.midoriya.core.model.mongo.Export
import org.hse.midoriya.core.model.mongo.dao.ExportRepository
import org.hse.midoriya.core.service.domain.Converter
import org.springframework.stereotype.Service

@Service
class ExportService(
    private val exportRepository: ExportRepository,
    private val converter: Converter
) {

    companion object {
        private const val RUSSIA_CODE: Int = 643
    }

    fun getImportByProductCode(productCode: String): List<List<HashMap<String, Any>>> {
        val exportList = exportRepository.findExportsByRussiaAndProductCode(
            russia = RUSSIA_CODE,
            cmdCode = productCode
        ).groupBy { it.year }
        val pointsList = exportList.map { export ->
            getPointMapFromExports(export)
        }
        return listOf(pointsList)
    }

    fun getGrossImportByTitle(title: String, productCode: String): List<List<HashMap<String, Any>>> {
        val codeList = Country.titleToCode(title)
        return if (codeList?.code?.size == 1) {
            getImportByCountry(codeList.code.first(), productCode)
        } else {
            getImportByCountries(codeList!!.code, productCode)
        }
    }

    private fun getImportByCountry(reporterCode: Int, productCode: String): List<List<HashMap<String, Any>>> {
        val exportList = exportRepository.getAllByRussiaAndReporterCodeAndProductCode(
            russia = RUSSIA_CODE,
            reporterCode = reporterCode,
            cmdCode = productCode)
        val pointsList = exportList.map { export ->
            getPointMapFromExport(export)
        }
        return listOf(pointsList)
    }

    private fun getPointMapFromExport(export: Export): HashMap<String, Any> {
        val pointMap = hashMapOf<String, Any>()
        val point = converter.exportToPoint(export)
        pointMap["x"] = point.x
        pointMap["y"] = point.y
        return pointMap
    }

    private fun getImportByCountries(reporterCodes: List<Int>, productCode: String): List<List<HashMap<String, Any>>> {
        val exportList = exportRepository.findExportsByRussiaAndReporterCodeInAndProductCode(
            russia = RUSSIA_CODE,
            reporterCodes = reporterCodes,
            cmdCode = productCode
        ).groupBy { it.year }
        val pointsList = exportList.map { export ->
            getPointMapFromExports(export)
        }
        return listOf(pointsList)
    }

    private fun getPointMapFromExports(exports: Map.Entry<String, List<Export>>): HashMap<String, Any> {
        val pointMap = hashMapOf<String, Any>()
        val point = converter.exportsToPoint(exports)
        pointMap["x"] = point.x
        pointMap["y"] = point.y
        return pointMap
    }
}