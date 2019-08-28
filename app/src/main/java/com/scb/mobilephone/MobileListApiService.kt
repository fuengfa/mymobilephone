package com.scb.mobilephone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MobileListApiService {

    @GET("api/mobiles/")
    fun mobile(): Call<List<MobileModel>>

    @GET("/api/mobiles/{mobile_id}/images/")
    fun pictures(
        @Path("mobile_id") mobile_Id :Int?
    ): Call<List<Pictures>>


}
