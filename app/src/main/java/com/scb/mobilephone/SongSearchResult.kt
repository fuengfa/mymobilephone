package com.scb.mobilephone

import com.google.gson.annotations.SerializedName

data class SongSearchResult(
        @SerializedName("resultCount") val resultCount: Int,
        @SerializedName("results") val results: List<MobileModel>
)
