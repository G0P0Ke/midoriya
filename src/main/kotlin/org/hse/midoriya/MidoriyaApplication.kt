package org.hse.midoriya

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class MidoriyaApplication

fun main(args: Array<String>) {
    runApplication<MidoriyaApplication>(*args)
}