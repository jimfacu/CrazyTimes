package com.example.crazytimes.Core.Service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceRetrofit {

    companion object{
        private val baseURL = "https://api.openweathermap.org/data/2.5/"
        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun<T> buildService(service:Class<T>):T = retrofit.create(service)
    }
}