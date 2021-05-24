package com.dc.rec.helpers

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val loggerSuffix = """\$.*$""".toRegex()

fun logger(lambda: () -> Unit): Logger {
    return LoggerFactory.getLogger(lambda.javaClass.name.replace(loggerSuffix, ""))
}