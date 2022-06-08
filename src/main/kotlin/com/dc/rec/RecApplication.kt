package com.dc.rec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecApplication

fun main(args: Array<String>) {
    runApplication<RecApplication>(*args)
}