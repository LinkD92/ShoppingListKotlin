package com.symbol.shoppinglist.ui.categoriesManage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.ui.ColorSquare

@Composable
fun ManageCategories(
    modifier: Modifier = Modifier,
    viewModel: ManageCategoriesViewModel = hiltViewModel()
) {
    val categories = viewModel.allCategories.observeAsState().value ?: listOf()
    ListOfCategories(modifier = modifier, categories = categories)

}

@Composable
fun CategoryItem(modifier: Modifier = Modifier, category: Category){
    Row(modifier = modifier) {
        Text(modifier = modifier, text = category.categoryName)
        Box(modifier = modifier
            .clip(RoundedCornerShape(20))
        ) {
            ColorSquare(modifier, category.categoryColor)
        }
    }
}
@Composable
fun ListOfCategories(modifier: Modifier, categories: List<Category>){
    LazyColumn(
        modifier = modifier
    ){
        items(categories, key = {it.categoryName}){ item ->
            CategoryItem(category = item)
        }
    }
}


@Composable
@Preview
fun Preview(){
//    CategoryItem()
}