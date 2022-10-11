package com.symbol.shoppinglist.ui.productAdd

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.ui.ColorSquare

@Composable
fun AddProduct(
    modifier: Modifier = Modifier,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories = viewModel.allCategories.observeAsState().value ?: listOf()

    Column {
        LabelAndPlaceHolder(viewModel.productName) {
            viewModel.updateName(it)
        }
        CategoriesDropDown(
            modifier,
            categories,
            viewModel.productCategory
        ) { category ->
            viewModel.chooseCategory(category) }
        AddButton(modifier, onClick = { viewModel.addProduct() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun CategoriesDropDown(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedCategory: Category,
    chooseCategory: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .clickable { expanded = !expanded },
        ) {
            Text(text = selectedCategory.categoryName)
            ColorSquare(color = selectedCategory.categoryColor)
            Icon(Icons.Rounded.ArrowDropDown, IconName.DROPDOWN)
            DropdownMenu(
                modifier = modifier,
                expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        chooseCategory(category)
                    }) {
                        Text(text = category.categoryName)
                        ColorSquare(modifier, category.categoryColor)
                    }
                }
            }
        }
    }
}
