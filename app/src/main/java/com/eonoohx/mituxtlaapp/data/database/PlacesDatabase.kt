package com.eonoohx.mituxtlaapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Place::class, Category::class], version = 1, exportSchema = false)
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var instance: PlacesDatabase? = null

        fun getDatabase(context: Context): PlacesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PlacesDatabase::class.java,
                    "place_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}