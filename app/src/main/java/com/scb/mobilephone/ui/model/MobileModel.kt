package com.scb.mobilephone.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MobileModel(
    @SerializedName("brand")
    val brand: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("thumbImageURL")
    val thumbImageURL: String,

    @SerializedName("fav")
    var fav: Int = 0

) : Parcelable