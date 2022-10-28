package com.symbol.shoppinglist.feature_category.data.data_source

import androidx.room.*
import com.symbol.shoppinglist.feature_category.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: Int): Category

    @Query("SELECT COUNT(*) FROM categories WHERE name = :name")
    suspend fun categoryNameCount(name: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)
}