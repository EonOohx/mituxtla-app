package com.eonoohx.mituxtlaapp.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface PlaceElement {
    val id: String
    val name: String
    val rating: Float
    val photoUrl: String
}

@Serializable
data class Place(
    override val id: String,
    override val name: String,
    override val rating: Float,
    @SerialName(value = "photo_url") override val photoUrl: String
) : PlaceElement

@Serializable
data class PlaceInfo(
    override val id: String,
    override val name: String,
    override val rating: Float,
    @SerialName(value = "photo_url") override val photoUrl: String,
    val address: String,
    val location: PlaceLocation?,
    @SerialName(value = "is_open") val isOpen: Boolean?,
    val website: String?,
    val phone: String?,
    val description: String
) : PlaceElement

@Serializable
data class PlaceLocation(
    val lat: Double, val lng: Double
)