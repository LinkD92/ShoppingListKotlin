package com.symbol.shoppinglist.feature_settings.domain.use_case

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.FullCategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_settings.domain.model.AppSettings
import com.symbol.shoppinglist.feature_settings.domain.repository.PreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
@OptIn(ExperimentalCoroutinesApi::class)

class GetSettingsTest {

    private lateinit var getSettings: GetSettings

    @Mock
    private lateinit var preferencesRepository: PreferencesRepository

    @Before
    fun setUp() {
        preferencesRepository = Mockito.mock(PreferencesRepository::class.java)
        getSettings = GetSettings(preferencesRepository)
    }

    @Test
    fun `getSettings should return flow AppSettings`() = runTest {
        // Given
        val categoryOrderType = CategoryOrderType.NAME
        val sortType = SortType.ASCENDING
        val fullCategoryOrderType = FullCategoryOrderType(categoryOrderType, sortType)
        val appSettings = flow {
            emit(AppSettings(fullCategoryOrderType = fullCategoryOrderType))
        }

        // When
        `when`(preferencesRepository.getSettings()).thenReturn(appSettings)

        // Then
        getSettings().test {
            val result = awaitItem()
            assertThat(result.fullCategoryOrderType.categoryOrderType)
                .isEqualTo(categoryOrderType)
            assertThat(result.fullCategoryOrderType.sortType).isEqualTo(sortType)
            cancelAndIgnoreRemainingEvents()
        }
    }
}