package com.br.apimercadolivre.general.http.providers

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> getApiEndpoint(baseURL: String, clazz: Class<T>): T =
    getApiEndpoint(baseURL, clazz, GsonConverterFactory.create())


fun <T> getApiEndpoint(baseURL: String, clazz: Class<T>, converter: Converter.Factory): T =
    Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(converter)
        .build()
        .create(clazz)