package com.br.apimercadolivre.utils

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.StringBuilder

fun BufferedReader.readFile(): String = readLines().joinTo(StringBuilder()).toString()


private fun basePathProject(): String {
    val path = "./app/src/test/assets/mocks/"
    val contains = File("./").list()?.contains("app") ?: false
    return if (contains) {
        path
    } else {
        "../${path}"
    }
}

fun readFromAssetsFolder(path: String): String {
    val newPath = String.format("%s/%s", basePathProject(), path)
    return try {
        val stream = FileInputStream(File(newPath))
        val buffer = ByteArray(stream.available())
        stream.read(buffer)
        stream.close()
        String(buffer)
    } catch (e: IOException) {
        ""
    }
}