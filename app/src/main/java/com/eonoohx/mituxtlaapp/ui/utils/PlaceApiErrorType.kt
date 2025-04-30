package com.eonoohx.mituxtlaapp.ui.utils

import androidx.annotation.StringRes
import com.eonoohx.mituxtlaapp.R

enum class PlaceApiErrorType(@StringRes val errorMessage: Int, @StringRes val contentDescription: Int) {
    NETWORK(errorMessage = R.string.network_error, contentDescription = R.string.no_connection),
    HTTP(errorMessage = R.string.http_error, contentDescription = R.string.request_timed_out),
    TIMEOUT(errorMessage = R.string.timeout_error, contentDescription = R.string.server_error)
}