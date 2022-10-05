package com.symbol.shoppinglist.ui.categoriesManage

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ManageCategories(
    modifier: Modifier = Modifier,
    viewModel: ManageCategoriesViewModel = hiltViewModel()
) {
    Text(text = "Manage Categories")
}