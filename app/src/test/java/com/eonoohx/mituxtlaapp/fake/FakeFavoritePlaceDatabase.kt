package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.DatabaseRepository
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FakeFavoritePlaceDatabase : DatabaseRepository {
    override fun getAllFavoritePlacesStream(): Flow<List<FavoritePlace>> =
        flow { emit(FakeDataSource.fakeFavoritePlaces) }

    override fun getFavoritePlace(id: String): Flow<FavoritePlace> =
        flow {
            val place = FakeDataSource.fakeFavoritePlaces.find { it.id == id }
            if (place != null) emit(place)
            else throw NoSuchElementException("Place with id $id not found")
        }

    override suspend fun insertFavoritePlace(favoritePlace: FavoritePlace) {
        FakeDataSource.fakeFavoritePlaces.add(favoritePlace)
    }

    override suspend fun updateFavoritePlaceStatus(id: String) {
        val place = FakeDataSource.fakeFavoritePlaces.find { it.id == id }
        place?.let {
            it.viewed = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-DD hh:mm:ss"))
        }
    }

    override suspend fun deleteFavoritePlace(favoritePlace: FavoritePlace) {
        FakeDataSource.fakeFavoritePlaces.removeIf { it == favoritePlace }
    }

    override suspend fun exists(id: String): Boolean {
        return FakeDataSource.fakeFavoritePlaces.any { it.id == id }
    }
}