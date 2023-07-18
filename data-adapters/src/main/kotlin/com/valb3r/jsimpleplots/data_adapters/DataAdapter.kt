package com.valb3r.jsimpleplots.data_adapters

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import java.io.File


class DataAdapter {

    companion object Data {

        fun csv(): Csv {
            return Csv()
        }

    }
}

class Csv {

    private val mapper = CsvMapper()

    private var separator = ';'

    private lateinit var data: List<List<Any>>
    var headers: List<String>? = null
        private set

    fun of(csvFile: File): Csv {
        val it: MappingIterator<List<String>> = mapper
            .readerForListOf(String::class.java)
            .with(CsvParser.Feature.WRAP_AS_ARRAY)
            .with(CsvSchema.emptySchema().withColumnSeparator(separator))
            .readValues(csvFile)
        var rowIndex = 0
        val data = arrayListOf<List<Any>>()
        do {
            val row = it.nextValue()
            if (0 == rowIndex && null == row[0].toFloatOrNull()) {
                this.headers = row
                continue
            }

            data += row.map { convert(it) }
            rowIndex++
        } while (it.hasNextValue())

        this.data = data
        return this
    }

    /**
     * Returns flattened CSV table. I.e. [row00,row01,row02, row10,row11,row12, ...]
     */
    fun flatTableOfFloats(): FloatArray {
        return this.data.flatten().map { it as Float }.toFloatArray()
    }

    fun column(name: String): Column {
        return column(this.headers!!.indexOf(name))
    }

    fun column(index: Int): Column {
        return Column(this.data.map { it[index] })
    }

    operator fun get(column: Int): Column {
        return column(column)
    }

    operator fun get(column: String): Column {
        return column(column)
    }

    private fun convert(value: String): Any {
        return value.toFloatOrNull() ?: value
    }
}

class Column(private val data: List<Any>) {

    fun float(): FloatArray {
        return this.data.map { it as Float }.toFloatArray()
    }

    fun any(): Array<Any> {
        return this.data.toTypedArray()
    }

    fun string(): Array<String> {
        return this.data.map { it as String }.toTypedArray()
    }
}