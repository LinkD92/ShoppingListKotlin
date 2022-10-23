package com.symbol.shoppinglist.database.local

import com.symbol.shoppinglist.feature_category.data.data_source.CategoriesDao
import com.symbol.shoppinglist.feature_product.data.data_source.ProductsDao

interface ListDatabase {
    fun productsDao(): ProductsDao
    fun categoriesDao(): CategoriesDao
}