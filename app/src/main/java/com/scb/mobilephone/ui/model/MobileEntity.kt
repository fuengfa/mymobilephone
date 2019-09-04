package com.scb.mobilephone.ui.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mobile")
data class MobileEntity(
    @PrimaryKey val id: Int,
    @NonNull var name: String,
    @NonNull val description: String,
    @NonNull val brand: String,
    val price: Double,
    val rating: Double,
    val thumbImageURL: String,
    var fav: Boolean
)