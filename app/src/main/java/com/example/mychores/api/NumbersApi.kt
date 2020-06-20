package com.example.mychores.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NumbersApi {
    companion object {

        private const val baseUrl = "http://numbersapi.com/"

        fun createApi(): NumbersApiService {

            val okHttpClient = OkHttpClient.Builder().
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

            val numbersApi = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()

            return numbersApi.create(NumbersApiService::class.java)
        }
    }
}