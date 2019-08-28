package com.scb.mobilephone

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
//    https://scb-test-mobile.herokuapp.com/api/mobiles/
    val countryService by lazy { createService<CountryApiService>("https://scb-test-mobile.herokuapp.com/") }

//    val artistService by lazy { createService<ArtistApiService>("http://54.251.183.191:3000/") }

    private inline fun <reified T> createService(baseUrl: String): T =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .run { create(T::class.java) }

}
