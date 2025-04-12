package com.eonoohx.mituxtlaapp.data.network

import com.eonoohx.mituxtlaapp.data.PlaceDetailsItem
import com.eonoohx.mituxtlaapp.data.PlaceItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    override val id: String,
    override val name: String,
    override val rating: Float,
    @SerialName(value = "photo_url") override val photoUrl: String
) : PlaceItem

@Serializable
data class PlaceInfo(
    override val id: String,
    override val name: String,
    override val rating: Float?,
    @SerialName(value = "photo_url")
    override val photoUrl: String,
    override val description: String?,
    override val address: String,
    override val website: String?,
    override val phone: String?,
    val location: PlaceLocation?,
    @SerialName(value = "is_open")
    val isOpen: Boolean?,
) : PlaceItem, PlaceDetailsItem

@Serializable
data class PlaceLocation(
    val lat: Double, val lng: Double
)