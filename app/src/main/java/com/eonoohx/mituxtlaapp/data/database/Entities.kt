package com.eonoohx.mituxtlaapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class FavoritePlace(
    @PrimaryKey
    val id: String,
    val name: String,
    val category: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    val viewed: String,
    val address: String?,
    val description: String?,
    @ColumnInfo(name = "lat_location")
    val latLocation: String? = null,
    @ColumnInfo(name = "lng_location")
    val lngLocation: String? = null,
    val phone: String? = null,
    val website: String? = null
)