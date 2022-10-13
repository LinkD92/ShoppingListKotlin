package com.symbol.shoppinglist.ui.categoriesManage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.navigation.CategoriesDirections
import com.symbol.shoppinglist.ui.ColorSquare

@Composable
fun ManageCategories(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ManageCategoriesViewModel = hiltViewModel()
) {
    val categories = viewModel.allCategories.observeAsState().value ?: listOf()
    ListOfCategories(
        modifier = modifier, categories = categories,
        onClick = { categoryId ->
            navHostController.navigate(CategoriesDirections.AddCategory.passArgument(categoryId))
        }
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(modifier: Modifier = Modifier, category: Category, onClick: (Int) -> Unit) {
    Row(modifier = modifier.clickable {
        onClick(category.id)
    }
    ) {
        Text(modifier = modifier, text = category.categoryName)
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(20))
        ) {
            ColorSquare(modifier, category.categoryColor)
        }
    }
}

@Composable
fun ListOfCategories(modifier: Modifier, categories: List<Category>, onClick: (Int) -> Unit) {
    LazyColumn(
        modifier = modifier
    ) {
        items(categories, key = { it.id }) { item ->
            CategoryItem(
                category = item,
                onClick = onClick
            )
        }
    }
}


@Composable
@Preview
fun Preview() {
//    CategoryItem()
}