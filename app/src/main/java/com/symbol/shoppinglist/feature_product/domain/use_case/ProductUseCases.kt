package com.symbol.shoppinglist.feature_product.domain.use_case

import com.symbol.shoppinglist.feature_category.domain.use_case.ReorderCategories


data class ProductUseCases(
    val getProducts: GetProducts,
    val deleteProduct: DeleteProduct,
    val insertProduct: InsertProduct,
    val getProduct: GetProduct,
    val expandCategory: ExpandCategory,
    val getCategoryProducts: GetCategoryProducts,
    val insertProducts: InsertProducts
    )