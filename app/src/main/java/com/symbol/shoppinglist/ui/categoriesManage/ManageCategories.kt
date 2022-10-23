package com.symbol.shoppinglist.ui.categoriesManage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.ManageCategoriesEvent
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.ManageCategoriesViewModel
import com.symbol.shoppinglist.navigation.CategoriesDirections

@Composable
fun ManageCategories(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ManageCategoriesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
//    val categories by viewModel.allCategories.collectAsStateLifecycleAware(initial = emptyList())
    ListOfCategories(
        modifier = modifier,
        categories = state.categories,
        onClick = { categoryId ->
            navHostController.navigate(CategoriesDirections.AddCategory.passArgument(categoryId))
        },
        deleteIconClick = { category -> viewModel.onEvent(ManageCategoriesEvent.DeleteCategory(category)) }
    )
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: ((Int) -> Unit)? = null,
    deleteIconClick: ((Category) -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    val chooseModifier =
        if (onClick != null) Modifier.clickable { onClick(category.id) } else Modifier
    Card(
        modifier = chooseModifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
        elevation = 3.dp,
        backgroundColor = Color(category.color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = category.name,
                textAlign = TextAlign.Left
            )
            if (deleteIconClick != null) {
                Icon(
                    Icons.Rounded.Delete, IconName.DELETE,
                    Modifier
                        .clickable {
                            deleteIconClick(category)
                        }
                        .align(Alignment.CenterVertically)
                )
            }
            if (content != null)
                content()
        }
    }
}

@Composable
fun ListOfCategories(
    modifier: Modifier,
    categories: List<Category>,
    onClick: (Int) -> Unit,
    deleteIconClick: (Category) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 10.dp
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            items(categories, key = { it.id }) { item ->
                CategoryItem(
                    category = item,
                    onClick = onClick,
                    deleteIconClick = deleteIconClick,
                )
                Spacer(modifier = Modifier.height(3.dp))
            }
        }
    }
}