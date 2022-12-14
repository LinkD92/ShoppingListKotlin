package com.symbol.shoppinglist.feature_product.domain.use_case

data class ProductUseCases(
    val getProducts: GetProducts,
    val deleteProduct: DeleteProduct,
    val insertProduct: InsertProduct,
    val getProduct: GetProduct,
    val getCategoryProducts: GetCategoryProducts,
    val insertProducts: InsertProducts
)