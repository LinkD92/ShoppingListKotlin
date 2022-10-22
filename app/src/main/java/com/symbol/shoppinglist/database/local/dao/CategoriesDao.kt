package com.symbol.shoppinglist.database.local.dao

import androidx.room.*
import com.symbol.shoppinglist.database.local.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: Int): Category

    @Query("SELECT COUNT(*) FROM categories WHERE name = :name")
    suspend fun doesCategoryExists(name: String): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)
}