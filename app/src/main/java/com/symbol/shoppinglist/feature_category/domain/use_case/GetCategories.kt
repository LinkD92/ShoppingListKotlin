package com.symbol.shoppinglist.feature_category.domain.use_case

import com.symbol.shoppinglist.core.domain.util.OrderType
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.repository.CategoriesRepository
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(private val repository: CategoriesRepository) {

    operator fun invoke(
        categoryOrder: CategoryOrder = CategoryOrder.Name(OrderType.Ascending)
    ): Flow<List<Category>>{
        return repository.getAllCategories().map { categories ->
            when(categoryOrder.orderType){
                is OrderType.Ascending -> categories.sortedBy { it.name }
                is OrderType.Descending -> categories.sortedByDescending { it.name }
            }
        }
    }
}