package com.symbol.shoppinglist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.database.entities.Product

@Database(entities = [Product::class, Category::class], version = 1)
abstract class ListDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao

}