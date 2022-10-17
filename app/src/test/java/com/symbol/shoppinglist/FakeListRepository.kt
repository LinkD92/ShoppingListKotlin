package com.symbol.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.symbol.shoppinglist.database.ListRepository
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts

class FakeListRepository : ListRepository {
    private val products = mutableListOf<Product>()
    private val categories = mutableListOf<Category>()
    private val categoriesWithProducts = mutableListOf<CategoryWithProducts>()
    private val allPrducts = MutableLiveData<List<Product>>()
    private val allCateogies = MutableLiveData<List<Category>>()
    private val allCategoriesWithProducts = MutableLiveData<List<CategoryWithProducts>>(categoriesWithProducts)

    private fun refreshProduct() {
        allPrducts.postValue(products)
    }

    private fun refreshCategories() {
        allCateogies.postValue(categories)
    }

    private fun refreshCategoriesWithProduct(){
        allCategoriesWithProducts.postValue(categoriesWithProducts)
    }

    override fun getAllProducts(): LiveData<List<Product>> {
        return allPrducts
    }

    override suspend fun getProduct(id: Int): Product {
        return products[id]
    }

    override suspend fun addProduct(product: Product) {
        products.add(product)
        refreshProduct()
    }

    override suspend fun doesProductExists(name: String): Int {
        var productsWithNameFounded = 0
        products.forEach {
            if (it.name == name) productsWithNameFounded++
        }
        return productsWithNameFounded
    }

    override suspend fun deleteProduct(product: Product) {
        products.remove(product)
        refreshProduct()
    }

    override suspend fun updateProduct(product: Product) {
        products[product.id] = product
    }

    override fun getAllCategories(): LiveData<List<Category>> {
        return allCateogies
    }

    override suspend fun getCategory(id: Int): Category {
        return categories[id]
    }

    override suspend fun doesCategoryExists(name: String): Int {
        var categoriesWithNameFounded = 0
        categories.forEach {
            if (it.name == name) categoriesWithNameFounded++
        }
        return categoriesWithNameFounded
    }

    override suspend fun addCategory(category: Category) {
        categories.add(category)
        refreshCategories()
    }

    override suspend fun deleteCategory(category: Category) {
        categories.remove(category)
        refreshCategories()
    }

    override suspend fun updateCategory(category: Category) {
        categories[category.id] = category
    }

    override fun getCategoriesWithProducts(): LiveData<List<CategoryWithProducts>> {
        categories.forEach { category ->
            val listOfProductsWithCategory = mutableListOf<Product>()
            products.forEach { product ->
                if (product.categoryId == category.id){
                    listOfProductsWithCategory.add(product)
                }
            }
            val categoryWithProduct = CategoryWithProducts(category, listOfProductsWithCategory)
            categoriesWithProducts.add(categoryWithProduct)
        }
        refreshCategoriesWithProduct()
        return allCategoriesWithProducts
    }


}




