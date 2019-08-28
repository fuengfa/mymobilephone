package com.scb.mobilephone

import retrofit2.Call
import retrofit2.http.GET

interface CountryApiService {

    @GET("api/mobiles/")
    fun countries(): Call<List<MobileModel>>
}
