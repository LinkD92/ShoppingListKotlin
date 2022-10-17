package com.symbol.shoppinglist.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product

@Database(entities = [Product::class, Category::class], version = 2)
abstract class ListDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
}