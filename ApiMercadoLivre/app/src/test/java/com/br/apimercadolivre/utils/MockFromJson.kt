package com.br.apimercadolivre.utils

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileReader
import java.lang.StringBuilder

fun provideGsonInstance() = Gson()

inline fun <reified T> Gson.fromJsonToObject(path: String) : T =
    fromJson(readFromAssetsFolder(path), T::class.java)