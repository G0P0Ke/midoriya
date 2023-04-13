package org.hse.midoriya.core.service

import org.hse.midoriya.core.model.mongo.Import
import org.hse.midoriya.core.enum.Country
import org.hse.midoriya.core.model.mongo.dao.ImportRepository
import org.hse.midoriya.core.service.domain.Converter
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Service
class ImportService(
    private val importRepository: ImportRepository,
    private val converter: Converter,
) {
    companion object {
        private const val RUSSIA_CODE: Int = 643
        private const val TOTAL_CODE = "TOTAL"
        private const val PETROLEUM = "27"
        private val sdf = SimpleDateFormat("yyyymm")
    }

    fun getNonPrimaryExportByTitle(title: String): List<List<HashMap<String, Any>>> {
        val codeList = Country.titleToCode(title)
        val totalExport: List<List<HashMap<String, Any>>>
        val petroleumExport: List<List<HashMap<String, Any>>>
        if (codeList?.code?.size == 1) {
            totalExport = getExportByCountry(codeList.code.first(), TOTAL_CODE)
            petroleumExport = getExportByCountry(codeList.code.first(), PETROLEUM)
        } else {
            totalExport = getExportByCountries(codeList!!.code, TOTAL_CODE)
            petroleumExport = getExportByCountries(codeList.code, PETROLEUM)
        }
        var index = 0
        totalExport
            .asSequence()
            .flatten()
            .forEach { point ->
                val newValue = point["y"].toString().toBigDecimal() -
                    petroleumExport.flatten()[index]["y"].toString().toBigDecimal()
                point["y"] = newValue
                ++index
            }
        return totalExport
    }


    fun getGrossExportByTitle(title: String, productCode: String): List<List<HashMap<String, Any>>> {
        val codeList = Country.titleToCode(title)
        return if (codeList?.code?.size == 1) {
            getExportByCountry(codeList.code.first(), productCode)
        } else {
            getExportByCountries(codeList!!.code, productCode)
        }
    }

    private fun getExportByCountry(reporterCode: Int, productCode: String): List<List<HashMap<String, Any>>> {
        val importList = importRepository.getAllByRussiaAndReporterCodeAndProductCode(
            russia = RUSSIA_CODE,
            reporterCode = reporterCode,
            cmdCode = productCode)
        val pointsList = importList.map { import ->
            getPointMapFromImport(import)
        }
        return listOf(pointsList)
    }

    private fun getPointMapFromImport(import: Import): HashMap<String, Any> {
        val pointMap = hashMapOf<String, Any>()
        val point = converter.importToPoint(import)
        pointMap["x"] = point.x
        pointMap["y"] = point.y
        return pointMap
    }

    private fun getExportByCountries(reporterCodes: List<Int>, productCode: String): List<List<HashMap<String, Any>>> {
        val importList = importRepository.findImportsByRussiaAndReporterCodeInAndProductCode(
            russia = RUSSIA_CODE,
            codeList = reporterCodes,
            cmdCode = productCode
        ).groupBy { it.year }
        val pointsList = importList.map { import ->
            getPointMapFromImports(import)
        }
        return listOf(pointsList)
    }

    private fun getPointMapFromImports(imports: Map.Entry<String, List<Import>>): HashMap<String, Any> {
        val pointMap = hashMapOf<String, Any>()
        val point = converter.importsToPoint(imports)
        pointMap["x"] = point.x
        pointMap["y"] = point.y
        return pointMap
    }
}