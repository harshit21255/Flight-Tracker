package com.example.flighttracker.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AviationStackApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.aviationstack.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AviationStackApi::class.java)
    }
}
