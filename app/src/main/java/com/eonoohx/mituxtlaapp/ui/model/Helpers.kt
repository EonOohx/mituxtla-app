package com.eonoohx.mituxtlaapp.ui.model

import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteDiskIOException
import com.eonoohx.mituxtlaapp.ui.utils.ApiErrorType
import com.eonoohx.mituxtlaapp.ui.utils.DataBaseErrorType
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> dbCallHelper(
    call: suspend () -> T,
    onError: (PlaceServiceUiState.Error) -> Unit = {}
) {
    try {
        call()
    } catch (e: IOException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.IO))
    } catch (e: SQLiteDiskIOException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.DISK))
    } catch (e: SQLiteDatabaseCorruptException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.CORRUPT))
    } catch (e: SQLiteCantOpenDatabaseException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.INACCESSIBLE))
    }
}

const val TIMEOUT = 20_000L

suspend fun <T> apiCallHelper(
    call: suspend () -> T
): PlaceServiceUiState<T> {
    return try {
        withTimeout(TIMEOUT) {
            PlaceServiceUiState.Success(call())
        }
    } catch (e: IOException) {
        PlaceServiceUiState.Error(ApiErrorType.NETWORK)
    } catch (e: HttpException) {
        PlaceServiceUiState.Error(ApiErrorType.HTTP)
    } catch (e: TimeoutCancellationException) {
        PlaceServiceUiState.Error(ApiErrorType.TIMEOUT)
    }
}