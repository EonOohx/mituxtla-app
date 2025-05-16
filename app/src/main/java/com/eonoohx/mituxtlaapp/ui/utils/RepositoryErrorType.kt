package com.eonoohx.mituxtlaapp.ui.utils

import androidx.annotation.StringRes
import com.eonoohx.mituxtlaapp.R

interface RepositoryErrorType {
    @get:StringRes
    val errorMessage: Int

    @get:StringRes
    val contentDescription: Int
}

enum class ApiErrorType(
    @StringRes override val errorMessage: Int,
    @StringRes override val contentDescription: Int
) :
    RepositoryErrorType {
    NETWORK(errorMessage = R.string.network_error, contentDescription = R.string.no_connection),
    HTTP(errorMessage = R.string.http_error, contentDescription = R.string.server_error),
    TIMEOUT(errorMessage = R.string.timeout_error, contentDescription = R.string.request_timed_out)
}

enum class DataBaseErrorType(
    @StringRes override val errorMessage: Int,
    @StringRes override val contentDescription: Int
) : RepositoryErrorType {
    IO(errorMessage = R.string.io_db_error, contentDescription = R.string.failed_to_load_data_db),
    DISK(
        errorMessage = R.string.db_error_disk,
        contentDescription = R.string.db_error_disk_description
    ),
    CORRUPT(
        errorMessage = R.string.db_error_corrupt,
        contentDescription = R.string.db_error_corrupt_description
    ),
    INACCESSIBLE(
        errorMessage = R.string.db_error_cant_open,
        contentDescription = R.string.db_error_cant_open_description
    )
}