package com.symbol.shoppinglist.ui.productAdd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
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
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.components.CategoryItem
import com.symbol.shoppinglist.feature_product.presentation.add_edit_product.AddEditProductEvent
import com.symbol.shoppinglist.feature_product.presentation.add_edit_product.AddEditProductViewModel
import com.symbol.shoppinglist.ui.ConfirmButton
import com.symbol.shoppinglist.ui.LabelAndPlaceHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditProduct(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackScope = rememberCoroutineScope()
    val categories = viewModel.categories.value
    val productName = viewModel.productName.value
    val productQuantity = viewModel.productQuantity.value
    val productCategory = viewModel.productCategory.value
    val labelName = stringResource(id = R.string.product_label_name)
    val labelAmount = stringResource(id = R.string.product_label_quantity)


    LaunchedEffect(true) {
        snackScope.launch {
            viewModel.eventFlow.collectLatest { message ->
                snackbarHostState.showSnackbar(message = context.getString(message.resourceString))
            }
        }
    }

    val labelAndPlaceholderModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 10.dp)
    Column(modifier = Modifier.fillMaxWidth()) {
        LabelAndPlaceHolder(
            labelAndPlaceholderModifier,
            productName, labelName
        ) {
            viewModel.onEvent(AddEditProductEvent.EnteredName(it))
        }
        LabelAndPlaceHolder(
            labelAndPlaceholderModifier,
            productQuantity,
            labelAmount,
            KeyboardOptions(keyboardType = KeyboardType.Number)
        ) {
            viewModel.onEvent(AddEditProductEvent.EnteredQuantity(it))
        }
        CategoriesDropDown(
            modifier,
            categories,
            productCategory
        ) { category ->
            viewModel.onEvent(AddEditProductEvent.ChooseCategory(category))
        }
        ConfirmButton(
            Modifier
                .padding(10.dp)
                .align(Alignment.End),
            onClick = { viewModel.onEvent(AddEditProductEvent.SaveProduct) })
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
