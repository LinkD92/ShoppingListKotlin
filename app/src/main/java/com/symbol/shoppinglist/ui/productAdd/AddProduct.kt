package com.symbol.shoppinglist.ui.productAdd

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDownCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.ui.ConfirmButton
import com.symbol.shoppinglist.ui.LabelAndPlaceHolder
import com.symbol.shoppinglist.ui.categoriesManage.CategoryItem
import com.symbol.shoppinglist.ui.collectAsStateLifecycleAware

@Composable
fun AddProduct(
    modifier: Modifier = Modifier,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories by viewModel.allCategories.collectAsStateLifecycleAware(initial = emptyList())
    val labelName = stringResource(id = R.string.product_label_name)
    val labelAmount = stringResource(id = R.string.product_label_quantity)
    val labelAndPlaceholderModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 10.dp)

    Column(modifier = Modifier.fillMaxWidth()) {
        LabelAndPlaceHolder(
            labelAndPlaceholderModifier,
            viewModel.productName, labelName
        ) {
            viewModel.updateName(it)
        }
        LabelAndPlaceHolder(
            labelAndPlaceholderModifier,
            viewModel.productQuantity.toString(),
            labelAmount,
            KeyboardOptions(keyboardType = KeyboardType.Number)
        ) {
            viewModel.updateQuantity(it)
        }
        CategoriesDropDown(
            modifier,
            categories ?: listOf(),
            viewModel.productCategory
        ) { category ->
            viewModel.chooseCategory(category)
        }
        ConfirmButton(
            Modifier
                .padding(10.dp)
                .align(Alignment.End), onClick = { viewModel.confirmButtonClick() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, context.getText(it), Toast.LENGTH_SHORT).show()
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
                .fillMaxWidth(0.8f)
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryItem(category = selectedCategory, onClick = { expanded = !expanded }) {
                Icon(Icons.Rounded.ArrowDropDownCircle, IconName.DROPDOWN)
            }
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 3.dp),
                        onClick = {
                            expanded = false
                            chooseCategory(category)
                        },
                    ) {
                        CategoryItem(Modifier, category)
                    }
                }
            }
        }
    }
}
