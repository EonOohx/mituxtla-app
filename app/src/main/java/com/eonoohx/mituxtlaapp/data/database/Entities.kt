package com.eonoohx.mituxtlaapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "place", foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Place(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int?,
    val name: String,
    val address: String,
    val description: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val viewed: Boolean,
    val location: String?,
    val phone: String?,
    val website: String?
)

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: Int,
    val name: String
)