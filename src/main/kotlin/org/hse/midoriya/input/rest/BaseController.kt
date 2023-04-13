package org.hse.midoriya.input.rest

import org.hse.midoriya.core.service.ProductCodeService
import org.hse.midoriya.core.service.domain.Converter
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
class BaseController(
    private val productCodeService: ProductCodeService,
    private val converter: Converter
) {

    @GetMapping("/info")
    fun globalExportPage(model: Model): String {
        val list = productCodeService.getAllProductCode()
        model["productCodeList"] = converter.productCodesToItems(list)
        model["selectForm"] = ProductCodeForm()
        return "info"
    }

    @RequestMapping("/info", method = [RequestMethod.POST])
    fun figureFourFormAction(
        @ModelAttribute("selectForm") selectForm: ProductCodeForm,
        model: Model,
    ): String? {
        val list = productCodeService.getAllProductCode()
        model["productCodeList"] = converter.productCodesToItems(list)
        model["productForm"] = selectForm
        productCodeService.askForNewDataForProductCode(data = selectForm)
        return "info"
    }
}