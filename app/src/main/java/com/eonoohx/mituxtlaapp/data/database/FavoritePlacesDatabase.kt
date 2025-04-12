package com.eonoohx.mituxtlaapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritePlace::class], version = 2, exportSchema = false)
abstract class FavoritePlacesDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var Instance: FavoritePlacesDatabase? = null

        fun getDatabase(context: Context): FavoritePlacesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FavoritePlacesDatabase::class.java,
                    "fav_places_database"
                ).fallbackToDestructiveMigration(true).build().also { Instance = it }
            }
        }
    }
}