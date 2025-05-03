package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.PlacesRepository
import com.eonoohx.mituxtlaapp.data.network.Place
import com.eonoohx.mituxtlaapp.data.network.PlaceInfo
import com.eonoohx.mituxtlaapp.ui.model.TIMEOUT
import com.eonoohx.mituxtlaapp.ui.utils.ApiErrorType
import kotlinx.coroutines.delay
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

class FakeNetworkPlaceRepository(
    var apiError: ApiErrorType? = null
) : PlacesRepository {


    override suspend fun getPlacesData(search: String): List<Place> {
        throwError(apiError)
        return FakeDataSource.fakePlaces
    }

    override suspend fun getPlaceInfoData(placeId: String): PlaceInfo {
        throwError(apiError)
        return FakeDataSource.fakePlaceInfo
    }

    private suspend fun throwError(errorType: ApiErrorType?) {
        if (errorType != null) {
            when (errorType) {
                ApiErrorType.HTTP -> {
                    val errorResponse = Response.error<Any>(
                        404,
                        "Fake error body".toResponseBody(null)
                    )
                    throw HttpException(errorResponse)
                }

                ApiErrorType.NETWORK -> throw IOException()
                ApiErrorType.TIMEOUT -> delay(TIMEOUT + 1)
            }
        }
    }
}