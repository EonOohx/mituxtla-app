package com.eonoohx.mituxtlaapp.fake

import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteDiskIOException
import com.eonoohx.mituxtlaapp.data.DatabaseRepository
import com.eonoohx.mituxtlaapp.data.database.FavoritePlace
import com.eonoohx.mituxtlaapp.ui.utils.DataBaseErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FakeFavoritePlaceDatabase(
    var throwDBError: DataBaseErrorType? = null
) : DatabaseRepository {

    override fun getAllFavoritePlacesStream(): Flow<List<FavoritePlace>> =
        flow {
            throwError(throwDBError)
            emit(FakeDataSource.fakeFavoritePlaces)
        }

    override fun getFavoritePlace(id: String): Flow<FavoritePlace> =
        flow {
            throwError(throwDBError)
            val place = FakeDataSource.fakeFavoritePlaces.find { it.id == id }
            if (place != null) emit(place)
            throw NoSuchElementException("No se encontró el elemento")
        }

    override suspend fun insertFavoritePlace(favoritePlace: FavoritePlace) {
        throwError(throwDBError)
        FakeDataSource.fakeFavoritePlaces.add(favoritePlace)
    }

    override suspend fun updateFavoritePlaceStatus(id: String) {
        throwError(throwDBError)
        val place = FakeDataSource.fakeFavoritePlaces.find { it.id == id }
        place?.let {
            it.viewed = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-DD hh:mm:ss"))
        }
    }

    override suspend fun deleteFavoritePlace(favoritePlace: FavoritePlace) {
        throwError(throwDBError)
        FakeDataSource.fakeFavoritePlaces.removeIf { it == favoritePlace }
    }

    override suspend fun exists(id: String): Boolean {
        throwError(throwDBError)
        return FakeDataSource.fakeFavoritePlaces.any { it.id == id }
    }

    private fun throwError(error: DataBaseErrorType?) {
        if (error != null) {
            when (error) {
                DataBaseErrorType.IO -> throw IOException("Error al leer la base de datos")
                DataBaseErrorType.DISK -> throw SQLiteDiskIOException("Ocurrió un error al acceder a los datos.")
                DataBaseErrorType.CORRUPT -> throw SQLiteDatabaseCorruptException("La información local está dañada")
                DataBaseErrorType.INACCESSIBLE -> throw SQLiteCantOpenDatabaseException("No se pudo acceder a la base de datos. Verifica tu almacenamiento")
            }
        }
    }
}