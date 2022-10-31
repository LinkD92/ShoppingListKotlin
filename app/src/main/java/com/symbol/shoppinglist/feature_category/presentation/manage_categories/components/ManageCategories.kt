package com.symbol.shoppinglist.feature_category.presentation.manage_categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.core.data.util.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.presentation.navigation.CategoriesDirections
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.ManageCategoriesEvent
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.ManageCategoriesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ManageCategories(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: ManageCategoriesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { message ->
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = context.getString(message.resourceString),
                    actionLabel = context.getString(R.string.action_undo)
                )
                if (result == SnackbarResult.ActionPerformed){
                    viewModel.onEvent(ManageCategoriesEvent.RestoreCategory)
                }
            }
        }
    }
    ListOfCategories(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        categories = state.categories,
        onClick = { categoryId ->
            navHostController.navigate(CategoriesDirections.AddCategory.passArgument(categoryId!!))
        },
        deleteIconClick = { category ->
            viewModel.onEvent(ManageCategoriesEvent.DeleteCategory(category))
        }
    )
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: ((Int?) -> Unit)? = null,
    deleteIconClick: ((Category) -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    val chooseModifier =
        if (onClick != null) modifier.clickable { onClick(category.id) } else modifier
    Card(
        modifier = chooseModifier,
        elevation = 5.dp,
        backgroundColor = Color(category.color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
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
                        .clickable { deleteIconClick(category) }
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
    onClick: (Int?) -> Unit,
    deleteIconClick: (Category) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 10.dp
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            items(categories, key = { it.id }) { item ->
                CategoryItem(
                    modifier = modifier,
                    category = item,
                    onClick = onClick,
                    deleteIconClick = deleteIconClick,
                )
                Spacer(modifier = Modifier.height(3.dp))
            }
        }
    }
}