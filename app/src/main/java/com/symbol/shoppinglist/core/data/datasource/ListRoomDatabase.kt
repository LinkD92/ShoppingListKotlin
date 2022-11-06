package com.symbol.shoppinglist.core.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product

@Database(entities = [Product::class, Category::class], version = 1)
abstract class ListRoomDatabase : RoomDatabase(), ListDatabase