package com.eonoohx.mituxtlaapp.data

import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.data.database.PlaceDao
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getAllFavoritePlacesStream(): Flow<List<FavoritePlace>>

    fun getFavoritePlace(id: String): Flow<FavoritePlace>

    suspend fun insertFavoritePlace(favoritePlace: FavoritePlace)

    suspend fun updateFavoritePlaceStatus(id: String)

    suspend fun deleteFavoritePlace(favoritePlace: FavoritePlace)

    suspend fun exists(id: String): Boolean
}

class UserPlacesRepository(private val placeDao: PlaceDao) : DatabaseRepository {

    override fun getAllFavoritePlacesStream(): Flow<List<FavoritePlace>> = placeDao.getPlaces()

    override fun getFavoritePlace(id: String): Flow<FavoritePlace> = placeDao.getPlace(id)

    override suspend fun insertFavoritePlace(favoritePlace: FavoritePlace) =
        placeDao.insert(favoritePlace)

    override suspend fun updateFavoritePlaceStatus(id: String) = placeDao.updatePlaceStatus(id)

    override suspend fun deleteFavoritePlace(favoritePlace: FavoritePlace) =
        placeDao.delete(favoritePlace)

    override suspend fun exists(id: String): Boolean = placeDao.exists(id)
}