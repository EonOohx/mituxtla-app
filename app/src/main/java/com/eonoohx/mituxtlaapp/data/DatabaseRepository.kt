package com.eonoohx.mituxtlaapp.data

import com.eonoohx.mituxtlaapp.data.database.Place
import com.eonoohx.mituxtlaapp.data.database.PlaceDao
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getAllFavoritePlacesStream(): Flow<List<Place>>

    fun getFavoritePlace(id: Int): Flow<Place>

    suspend fun insertFavoritePlace(place: Place)

    suspend fun updateFavoritePlaceStatus(id: Int)

    suspend fun deleteFavoritePlace(place: Place)
}

class UserPlacesRepository(private val placeDao: PlaceDao) : DatabaseRepository {
    override fun getAllFavoritePlacesStream(): Flow<List<Place>> = placeDao.getPlaces()

    override fun getFavoritePlace(id: Int): Flow<Place> = placeDao.getPlace(id)

    override suspend fun insertFavoritePlace(place: Place) = placeDao.insert(place)

    override suspend fun updateFavoritePlaceStatus(id: Int) = placeDao.updatePlaceStatus(id)

    override suspend fun deleteFavoritePlace(place: Place) = placeDao.delete(place)
}