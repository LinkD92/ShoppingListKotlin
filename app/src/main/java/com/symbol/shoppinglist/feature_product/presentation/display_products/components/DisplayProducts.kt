package com.symbol.shoppinglist.feature_product.presentation.display_products.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.presentation.components.OptionsDialog
import com.symbol.shoppinglist.core.presentation.navigation.ProductsDirections
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.ProductPromptMessage
import com.symbol.shoppinglist.feature_product.presentation.display_products.DisplayProductsEvent
import com.symbol.shoppinglist.feature_product.presentation.display_products.DisplayProductsViewModel
import com.symbol.shoppinglist.ui.collectAsStateLifecycleAware
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DisplayProducts(
    navHostController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState,
    viewModel: DisplayProductsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    var openDialog by remember { mutableStateOf(false) }
    var productId by remember { mutableStateOf(0) }
    val snackScope = rememberCoroutineScope()
    val context = LocalContext.current
    val products = viewModel.productsOfCategoryState.collectAsStateLifecycleAware(initial = emptyMap())

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { message ->
            snackScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = context.getString(message.resourceString),
                    actionLabel = if (message is ProductPromptMessage.ProductDeleted) context.getString(
                        R.string.action_undo
                    )
                    else null
                )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.onEvent(DisplayProductsEvent.RestoreProduct)
                }
            }
        }
    }

    if (openDialog) OptionsDialog(shouldOpenDialog = { openDialog = false },
        title = stringResource(id = R.string.choose_action),
        btn1Name = stringResource(id = R.string.action_edit), btn1Icon = Icons.Rounded.Edit,
        btn1OnClick = {
            openDialog = false
            navHostController.navigate(ProductsDirections.AddProduct.passArgument(productId))
        },
        btn2Name = stringResource(id = R.string.action_delete), btn2Icon = Icons.Rounded.Delete,
        btn2OnClick = {
            openDialog = false
            viewModel.onEvent(DisplayProductsEvent.DeleteProduct(productId))
        })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        items(
            items = state.value.categories,
            key = {category: Category -> category.id}
        ) { category ->
            Log.d("Recomposition - DisplayProducts:", "Recomposition2")
//            val products = viewModel.getCategoriesProduct(category.id).collectAsStateLifecycleAware(
//                initial = emptyList()
//            ).value
//            val products = viewModel.testState.collectAsStateLifecycleAware(initial = emptyMap()).value[category] ?: emptyMap()

            Spacer(modifier = Modifier.padding(4.dp))
            ExpandableCategoryCard(
                modifier = Modifier.fillMaxSize(),
                category = category,
                expandIconOnClick = {
                    viewModel.onEvent(DisplayProductsEvent.ExpandCategory(category))
                },
                categoryCardMenu = {
                    DropdownMenuItem(
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                        onClick = {
                            viewModel.onMenuEvent(CategoryCardMenuEvent.CheckUncheckAll(products.value[category] ?: emptyList()))
                        }
                    ) {
                        Text(text = "Check/Uncheck all")
                    }
                })
            {
                Log.d("Recomposition - DisplayProducts:", "Recomposition3")
                ProductItemsList(modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(10.dp),
                    backgroundColor = category.color,
                    products = products.value[category] ?: emptyList(),
                    onItemClick = { product ->
                        viewModel.onEvent(DisplayProductsEvent.ChangeProductSelection(product))
                    },
                    onLongClick = { product ->
                        productId = product.id
                        openDialog = true
                    }
                )
            }
        }
    }
}