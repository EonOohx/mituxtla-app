package com.eonoohx.mituxtlaapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eonoohx.mituxtlaapp.data.PlaceDetailsItem
import com.eonoohx.mituxtlaapp.data.PlaceItem

@Entity(tableName = "place")
data class FavoritePlace(
    @PrimaryKey
    override val id: String,
    override val name: String,
    override val rating: Float?,
    @ColumnInfo(name = "photo_url")
    override val photoUrl: String,
    override val address: String? = null,
    override val description: String?,
    override val phone: String? = null,
    override val website: String? = null,
    @ColumnInfo(name = "lat_location")
    val latLocation: String? = null,
    @ColumnInfo(name = "lng_location")
    val lngLocation: String? = null,
    val category: String,
    var viewed: String,
) : PlaceItem, PlaceDetailsItem