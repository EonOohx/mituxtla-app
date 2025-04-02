package com.eonoohx.mituxtlaapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Place::class, Category::class], version = 1, exportSchema = false)
abstract class FavoritePlacesDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var instance: FavoritePlacesDatabase? = null

        fun getDatabase(context: Context): FavoritePlacesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FavoritePlacesDatabase::class.java,
                    "fav_places_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}