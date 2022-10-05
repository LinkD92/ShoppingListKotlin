package com.symbol.shoppinglist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.symbol.shoppinglist.database.entities.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

}