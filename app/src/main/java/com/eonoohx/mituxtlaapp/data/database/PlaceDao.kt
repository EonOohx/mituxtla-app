package com.eonoohx.mituxtlaapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: Place) // Entity Place here

    @Delete
    suspend fun delete(place: Place)

    @Query("SELECT * from place ORDER BY name ASC") // ASC ordering in ascending order
    fun getPlaces(id: Int): Flow<List<Place>>

    @Query("SELECT * from place WHERE id = :id")
    fun getPlace(id: Int): Flow<Place>
}