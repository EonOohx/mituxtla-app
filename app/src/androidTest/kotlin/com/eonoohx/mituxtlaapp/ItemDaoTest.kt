package com.eonoohx.mituxtlaapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.eonoohx.mituxtlaapp.data.database.FavoritePlacesDatabase
import com.eonoohx.mituxtlaapp.data.database.PlaceDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var placeDao: PlaceDao
    private lateinit var favoritePlacesDatabase: FavoritePlacesDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        favoritePlacesDatabase =
            Room.inMemoryDatabaseBuilder(context, FavoritePlacesDatabase::class.java)
                .allowMainThreadQueries().build()
        placeDao = favoritePlacesDatabase.placeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        favoritePlacesDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOnItemToDb()

        val allItems = placeDao.getPlaces().first()
        val place = placeDao.getPlace(allItems[0].id).first()

        assertEquals(place, FakeDataSource.fakeFavoritePlaces[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()

        val allItems = placeDao.getPlaces().first()
        val place1 = placeDao.getPlace(allItems[0].id).first()
        val place2 = placeDao.getPlace(allItems[1].id).first()

        assertEquals(place2, FakeDataSource.fakeFavoritePlaces[1])
        assertEquals(place1, FakeDataSource.fakeFavoritePlaces[2])
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItem_updatesItemInDB() = runBlocking {
        addOnItemToDb()

        var allItems = placeDao.getPlaces().first()
        val placeItem = allItems[0]
        val placeTimeStamp = placeItem.viewed

        placeDao.updatePlaceStatus(placeItem.id)
        allItems = placeDao.getPlaces().first()

        assertNotSame(placeTimeStamp, allItems[0].viewed)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItem_deleteItemInDB() = runBlocking {
        addOnItemToDb()

        placeDao.delete(FakeDataSource.fakeFavoritePlaces[0])
        val allItems = placeDao.getPlaces().first()

        assertTrue(allItems.isEmpty())
    }

    // Utility Functions

    private suspend fun addOnItemToDb() {
        placeDao.insert(FakeDataSource.fakeFavoritePlaces[0])
    }

    private suspend fun addTwoItemsToDb() {
        placeDao.insert(FakeDataSource.fakeFavoritePlaces[1])
        placeDao.insert(FakeDataSource.fakeFavoritePlaces[2])
    }
}