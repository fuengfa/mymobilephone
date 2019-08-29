package com.scb.mobilephone.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MobileModel(
    @SerializedName("brand")val brand: String,
    @SerializedName("description")val description: String,
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("price")val price: Double,
    @SerializedName("rating")val rating: Double,
    @SerializedName("thumbImageURL")val thumbImageURL: String,
    @SerializedName("fav")var fav: Int = 0

) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(brand)
        parcel.writeString(description)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeDouble(rating)
        parcel.writeString(thumbImageURL)
        parcel.writeInt(fav)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MobileModel> {
        override fun createFromParcel(parcel: Parcel): MobileModel {
            return MobileModel(parcel)
        }

        override fun newArray(size: Int): Array<MobileModel?> {
            return arrayOfNulls(size)
        }
    }

}