package com.dc.rec.commons.csv

import com.opencsv.CSVReaderBuilder
import java.io.Reader

class CsvReader {

    fun <T> read(reader: Reader, toObject: (Array<String>) -> T): List<T> {
            CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .build()
                    .use {
                        return it.readAll().map(toObject)
                    }
    }
}

