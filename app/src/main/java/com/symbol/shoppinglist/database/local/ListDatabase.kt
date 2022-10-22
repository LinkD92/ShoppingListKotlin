package com.symbol.shoppinglist.database.local

import com.symbol.shoppinglist.database.local.dao.CategoriesDao
import com.symbol.shoppinglist.database.local.dao.ProductsDao

interface ListDatabase {
    fun productsDao(): ProductsDao
    fun categoriesDao(): CategoriesDao
}