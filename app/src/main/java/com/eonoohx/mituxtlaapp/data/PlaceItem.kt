package com.eonoohx.mituxtlaapp.data

interface PlaceItem {
    val id: String
    val name: String
    val rating: Float?
    val photoUrl: String
}

interface PlaceDetailsItem {
    val address: String?
    val website: String?
    val phone: String?
    val description: String?
}