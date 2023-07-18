package com.valb3r.jsimpleplots.data_adapters

import java.io.File

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val csv = DataAdapter.csv().of(File("data/results.csv"))
            println(csv.column("Learning rate"))
        }
    }
}