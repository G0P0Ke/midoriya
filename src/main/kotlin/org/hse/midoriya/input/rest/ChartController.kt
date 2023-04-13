package org.hse.midoriya.input.rest

import org.hse.midoriya.core.enum.Country
import org.hse.midoriya.core.service.ExportService
import org.hse.midoriya.core.service.ImportService
import org.hse.midoriya.core.service.ProductCodeService
import org.hse.midoriya.core.service.domain.CountryForm
import org.hse.midoriya.core.service.domain.ProductCodeForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Controller
@RequestMapping("/russian-trade")
class ChartController(
    val importService: ImportService,
    val exportService: ExportService,
    val productCodeService: ProductCodeService
) {

    companion object {
        private const val CHINA = "China"
        private const val TOTAL_CODE = "TOTAL"
        private const val PETROLEUM = "27"
        private const val PHARMACEUTICAL = "30"
    }

    @GetMapping("/global-export")
    fun globalExportPage(model: Model): String {
        model["selectForm"] = CountryForm()
        model["country"] = CHINA
        val list = importService.getGrossExportByTitle(CHINA, TOTAL_CODE)
        customizeCountryModel(list, model)
        return "figureOne"
    }

    @RequestMapping("/global-export", method = [RequestMethod.POST])
    fun figureOneFormAction(
        @ModelAttribute("selectForm") selectForm: CountryForm,
        model: Model,
    ): String? {
        model["selectForm"] = selectForm
        model["country"] = selectForm.title
        val list = importService.getGrossExportByTitle(selectForm.title, TOTAL_CODE)
        customizeCountryModel(list, model)
        return "figureOne"
    }

    @GetMapping("/petroleum-export")
    fun petroleumPage(model: Model): String {
        model["selectForm"] = CountryForm()
        model["country"] = CHINA
        val list = importService.getGrossExportByTitle(CHINA, PETROLEUM)
        customizeCountryModel(list, model)
        return "figureTwo"
    }

    @RequestMapping("/petroleum-export", method = [RequestMethod.POST])
    fun figureTwoFormAction(
        @ModelAttribute("selectForm") selectForm: CountryForm,
        model: Model,
    ): String? {
        model["selectForm"] = selectForm
        model["country"] = selectForm.title
        val list = importService.getGrossExportByTitle(selectForm.title, PETROLEUM)
        customizeCountryModel(list, model)
        return "figureTwo"
    }

    @GetMapping("/non-primary-export")
    fun nonPrimaryPage(model: Model): String {
        model["selectForm"] = CountryForm()
        model["country"] = CHINA
        val list = importService.getNonPrimaryExportByTitle(CHINA)
        customizeCountryModel(list, model)
        return "figureThree"
    }

    @RequestMapping("/non-primary-export", method = [RequestMethod.POST])
    fun figureThreeFormAction(
        @ModelAttribute("selectForm") selectForm: CountryForm,
        model: Model,
    ): String? {
        model["selectForm"] = selectForm
        model["country"] = selectForm.title
        val list = importService.getNonPrimaryExportByTitle(selectForm.title)
        customizeCountryModel(list, model)
        return "figureThree"
    }

    @GetMapping("/global-import")
    fun globalImportPage(model: Model): String {
        model["selectForm"] = CountryForm()
        model["country"] = CHINA
        val list = exportService.getGrossImportByTitle(CHINA, TOTAL_CODE)
        customizeCountryModel(list, model)
        return "figureFour"
    }

    @RequestMapping("/global-import", method = [RequestMethod.POST])
    fun figureFourFormAction(
        @ModelAttribute("selectForm") selectForm: CountryForm,
        model: Model,
    ): String? {
        model["selectForm"] = selectForm
        model["country"] = selectForm.title
        val list = exportService.getGrossImportByTitle(selectForm.title, TOTAL_CODE)
        customizeCountryModel(list, model)
        return "figureFour"
    }

    @GetMapping("/category-import")
    fun categoryImportPage(model: Model): String {
        model["selectForm"] = ProductCodeForm()
        model["productCode"] = PHARMACEUTICAL
        val list = exportService.getImportByProductCode(PHARMACEUTICAL)
        customizeProductMode(list, model)
        return "figureFive"
    }

    @RequestMapping("/category-import", method = [RequestMethod.POST])
    fun figureFiveFormAction(
        @ModelAttribute("selectForm") selectForm: ProductCodeForm,
        model: Model,
    ): String? {
        model["selectForm"] = selectForm
        model["productCode"] = selectForm.code
        val list = exportService.getImportByProductCode(selectForm.code)
        customizeProductMode(list, model)
        return "figureFive"
    }

    private fun customizeCountryModel(list: List<List<HashMap<String, Any>>>, model: Model) {
        val period = getPeriod(list)
        model["firstYear"] = period.first ?: "empty"
        model["lastYear"] = period.second ?: "empty"
        model["dataPointsList"] = list
        model["countryList"] = Country.values()
    }

    private fun customizeProductMode(list: List<List<HashMap<String, Any>>>, model: Model) {
        val period = getPeriod(list)
        model["firstYear"] = period.first ?: "empty"
        model["lastYear"] = period.second ?: "empty"
        model["dataPointsList"] = list
        model["codeList"] = productCodeService.getActiveProductCode()
    }

    private fun getPeriod(list: List<List<HashMap<String, Any>>>): Pair<Any?, Any?> {
        val points = list.firstOrNull()
        val firstYear = points?.first()?.get("x")
        val lastYear = points?.last()?.get("x")
        return Pair(firstYear, lastYear)
    }
}