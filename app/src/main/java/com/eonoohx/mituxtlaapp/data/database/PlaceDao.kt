package com.eonoohx.mituxtlaapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoritePlace: FavoritePlace) // Entity Place here

    @Delete
    suspend fun delete(favoritePlace: FavoritePlace)

    @Transaction
    @Query("SELECT id, name, photo_url, category, viewed FROM place ORDER BY category ASC") // ASC ordering in ascending order
    fun getPlaces(): Flow<List<FavoritePlace>>

    @Transaction
    @Query("SELECT * FROM place WHERE id = :id")
    fun getPlace(id: String): Flow<FavoritePlace>

    @Query("UPDATE place SET viewed = CURRENT_TIMESTAMP WHERE id = :id")
    suspend fun updatePlaceStatus(id: String)

    @Query("SELECT EXISTS (SELECT 1 FROM place WHERE id = :id)")
    suspend fun exists(id: String): Boolean
}