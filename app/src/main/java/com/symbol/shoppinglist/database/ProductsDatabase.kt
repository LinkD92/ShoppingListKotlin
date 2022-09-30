package com.symbol.shoppinglist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.symbol.shoppinglist.data.Product
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Product::class], version = 1)
abstract class ProductsLocalDataSource : RoomDatabase() {
    abstract fun productDao(): ProductDao

    class Callback @Inject constructor(
        private val database: Provider<ProductsLocalDataSource>
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            database.get()
        }
    }
}