package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.data.network.PlaceLocation

object FakeDataSource {
    val fakePlaces: List<Place> = listOf(
        Place(
            id = "place_001",
            name = "Louvre Museum",
            rating = 4.8f,
            photoUrl = "https://example.com/photos/louvre.jpg"
        ),
        Place(
            id = "place_002",
            name = "Colosseum",
            rating = 4.7f,
            photoUrl = "https://example.com/photos/colosseum.jpg"
        ),
    )

    val fakePlaceInfo: PlaceInfo = PlaceInfo(
        id = "place_004",
        name = "Golden Gate Bridge",
        rating = 4.6f,
        photoUrl = "https://example.com/photos/golden_gate.jpg",
        address = "Golden Gate Bridge, San Francisco, CA, USA",
        location = PlaceLocation(
            lat = 37.819929,
            lng = -122.478255
        ),
        isOpen = null, // ejemplo con valor nulo
        website = null,
        phone = null,
        description = "A suspension bridge spanning the Golden Gate strait, the one-mile-wide channel between San Francisco Bay and the Pacific Ocean."
    )

    val fakeFavoritePlaces = mutableListOf(
        FavoritePlace(
            id = "place_001",
            name = "Gran Pirámide de Giza",
            category = "Monumento Histórico",
            photoUrl = "https://example.com/photos/piramide_giza.jpg",
            viewed = "2025-04-01",
            address = "Al Haram, Nazlet El-Semman, Al Giza Desert, Giza Governorate, Egipto",
            description = "La única maravilla del mundo antiguo que aún existe, construida como tumba para el faraón Keops.",
            latLocation = "29.9792",
            lngLocation = "31.1342",
            phone = "+20 2 3377 6936",
            website = "https://www.egypt.travel/en/attractions/great-pyramid-of-giza"
        ),
        FavoritePlace(
            id = "place_002",
            name = "Machu Picchu",
            category = "Sitio Arqueológico",
            photoUrl = "https://example.com/photos/machu_picchu.jpg",
            viewed = "2025-03-28",
            address = "Machu Picchu, 08680, Perú",
            description = "Antigua ciudad inca enclavada en las montañas de los Andes, conocida por su arquitectura sofisticada y vistas panorámicas.",
            latLocation = "-13.1631",
            lngLocation = "-72.5450",
            phone = "+51 84 582030",
            website = "https://www.machupicchu.gob.pe"
        ),
        FavoritePlace(
            id = "place_003",
            name = "Taj Mahal",
            category = "Mausoleo",
            photoUrl = "https://example.com/photos/taj_mahal.jpg",
            viewed = "2025-03-15",
            address = "Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh 282001, India",
            description = "Mausoleo de mármol blanco construido por el emperador Shah Jahan en memoria de su esposa Mumtaz Mahal.",
            latLocation = "27.1751",
            lngLocation = "78.0421",
            phone = "+91 562 222 7261",
            website = "https://www.tajmahal.gov.in"
        )
    )

}