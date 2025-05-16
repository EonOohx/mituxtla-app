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
    } catch (_: IOException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.IO))
    } catch (_: SQLiteDiskIOException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.DISK))
    } catch (_: SQLiteDatabaseCorruptException) {
        onError(PlaceServiceUiState.Error(DataBaseErrorType.CORRUPT))
    } catch (_: SQLiteCantOpenDatabaseException) {
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
    } catch (_: IOException) {
        PlaceServiceUiState.Error(ApiErrorType.NETWORK)
    } catch (_: HttpException) {
        PlaceServiceUiState.Error(ApiErrorType.HTTP)
    } catch (_: TimeoutCancellationException) {
        PlaceServiceUiState.Error(ApiErrorType.TIMEOUT)
    }
}