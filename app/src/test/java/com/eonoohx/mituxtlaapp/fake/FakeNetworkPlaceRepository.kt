package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.PlacesRepository
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import kotlinx.coroutines.delay
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

class FakeNetworkPlaceRepository(
    val httpError: Boolean = false,
    val networkError: Boolean = false,
    val timeoutError: Boolean = false
) : PlacesRepository {


    override suspend fun getPlacesData(search: String): List<Place> {
        throwError()
        return FakeDataSource.fakePlaces
    }

    override suspend fun getPlaceInfoData(placeId: String): PlaceInfo {
        throwError()
        return FakeDataSource.fakePlaceInfo
    }

    private suspend fun throwError() {
        if (httpError) {
            val errorResponse = Response.error<Any>(
                404,
                "Fake error body".toResponseBody(null)
            )
            throw HttpException(errorResponse)
        }
        if (networkError) throw IOException()
        if (timeoutError) delay(11_000)
    }
}