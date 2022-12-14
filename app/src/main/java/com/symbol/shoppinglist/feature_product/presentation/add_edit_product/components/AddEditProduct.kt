package com.symbol.shoppinglist.feature_product.presentation.add_edit_product.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDownCircle
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.data.util.IconName
import com.symbol.shoppinglist.core.presentation.components.CustomButton
import com.symbol.shoppinglist.core.presentation.components.LabelAndPlaceHolder
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.components.CategoryItem
import com.symbol.shoppinglist.feature_product.presentation.add_edit_product.AddEditProductEvent
import com.symbol.shoppinglist.feature_product.presentation.add_edit_product.AddEditProductViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditProduct(
    snackbarHostState: SnackbarHostState, viewModel: AddEditProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackScope = rememberCoroutineScope()
    val categories = viewModel.categoriesState.value
    val productName = viewModel.productNameState.value
    val productQuantity = viewModel.productQuantityState.value
    val productCategory = viewModel.productCategoryState.value
    val labelAndPlaceholderModifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)

    LaunchedEffect(true) {
        snackScope.launch {
            viewModel.eventFlow.collectLatest { message ->
                snackbarHostState.showSnackbar(message = context.getString(message.resourceString))
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        LabelAndPlaceHolder(
            labelAndPlaceholderModifier, productName,
            stringResource(id = R.string.product_label_name)
        ) {
            viewModel.onEvent(AddEditProductEvent.EnteredName(it))
        }
        LabelAndPlaceHolder(
            labelAndPlaceholderModifier, productQuantity,
            stringResource(id = R.string.product_label_quantity),
            KeyboardOptions(keyboardType = KeyboardType.Number)
        ) {
            viewModel.onEvent(AddEditProductEvent.EnteredQuantity(it))
        }
        CategoriesDropDown(
            labelAndPlaceholderModifier, categories, productCategory
        ) { category ->
            viewModel.onEvent(AddEditProductEvent.ChooseCategory(category))
        }
        CustomButton(
            onClick = { viewModel.onEvent(AddEditProductEvent.SaveProduct) }, Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .align(Alignment.End),
            icon = Icons.Rounded.Check
        )
    }
}

@Composable
fun CategoriesDropDown(
    modifier: Modifier = Modifier, categories: List<Category>, selectedCategory: Category,
    chooseCategory: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryItem(category = selectedCategory) {
                Icon(Icons.Rounded.ArrowDropDownCircle, IconName.DROPDOWN)
            }
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                        onClick = {
                            expanded = false
                            chooseCategory(category)
                        }) {
                        CategoryItem(category, Modifier)
                    }
                }
            }
        }
    }
}