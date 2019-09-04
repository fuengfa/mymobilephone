package com.scb.mobilephone.ui.model

import com.google.gson.annotations.SerializedName

data class MobileSearchResult(
        @SerializedName("resultCount")
        val resultCount: Int,
        @SerializedName("results")
        val results: List<MobileModel>
)
