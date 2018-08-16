package com.manwinder.nasapictureoftheday.api

import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NasaApi {
    @GET("/planetary/apod?api_key=NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo")
    fun getPictureOfTheDay() : Call<NasaResponse>

    companion object Factory {
        fun getNasaApi(NASA_API_ENDPOINT: HttpUrl = HttpUrl.parse("https://api.nasa.gov")!!): NasaApi {
            return Retrofit.Builder()
                    .baseUrl(NASA_API_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NasaApi::class.java)
        }
    }
}