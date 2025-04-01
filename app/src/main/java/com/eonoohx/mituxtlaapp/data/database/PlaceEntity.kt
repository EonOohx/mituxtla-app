package com.eonoohx.mituxtlaapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class Place(
    @PrimaryKey
    val id: Int,
    val name: String,
    val address: String,
    val description: String,
    val photoUrl: String,
    val location: String?,
    val phone: String?,
    val website: String?
)