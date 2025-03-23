package com.eonoohx.mituxtlaapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: String,
    val name: String,
    val rating: Float,
    @SerialName(value = "photo_url") val photoUrl: String
)