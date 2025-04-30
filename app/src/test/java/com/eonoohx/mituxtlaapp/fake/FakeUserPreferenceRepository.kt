package com.eonoohx.mituxtlaapp.fake

import com.eonoohx.mituxtlaapp.data.preference.AppTheme
import com.eonoohx.mituxtlaapp.data.preference.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserPreferenceRepository : PreferenceRepository {
    override val selectedTheme: Flow<AppTheme> = flow {}
    override suspend fun saveThemePreference(theme: AppTheme) {}
}