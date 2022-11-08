package com.symbol.shoppinglist.feature_settings.domain.use_case

import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class SaveDisplayProductsCategoriesOrderTest {

    private lateinit var getSettings: GetSettings

    @Mock
    private lateinit var preferencesRepository: PreferencesRepository

    @Before
    fun setUp() {
        preferencesRepository = Mockito.mock(PreferencesRepository::class.java)
        getSettings = GetSettings(preferencesRepository)
    }
}