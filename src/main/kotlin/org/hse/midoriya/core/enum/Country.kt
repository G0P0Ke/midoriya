package org.hse.midoriya.core.enum

enum class Country(
    val code: List<Int>,
    val title: String,
) {
    CHINA(code = listOf(156), title = "China"),
    JAPAN(code = listOf(392), title = "Japan"),
    KOREA(code = listOf(410), title = "South Korea"),
    INDIA(code = listOf(699), title = "India"),
    TURKEY(code = listOf(792), title = "Turkey"),
    UNITED_KINGDOM(code = listOf(826), title = "United Kingdom"),
    USA(code = listOf(842), title = "USA"),
    EU27(code = listOf(40,56,100,191,196,203,208,233,246,250,276,300,348,372,380,428,
        440,442,470,528,616,620,642,703,705,724,752), title = "EU27"),
    ALL(code = listOf(40,56,100,191,196,203,208,233,246,250,276,300,348,372,380,428,440,
        442,470,528,616,620,642,703,705,724,752,156,392,410,699,792,826,842), title = "34 countries");

    companion object {
        fun titleToCode(title: String): Country? {
            return when (title) {
                "China" -> CHINA
                "Japan" -> JAPAN
                "South Korea" -> KOREA
                "India" -> INDIA
                "Turkey" -> TURKEY
                "United Kingdom" -> UNITED_KINGDOM
                "USA" -> USA
                "EU27" -> EU27
                "34 countries" -> ALL
                else -> null
            }
        }
    }
}