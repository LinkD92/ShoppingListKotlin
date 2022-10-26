package com.symbol.shoppinglist.feature_product.presentation.display_products.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
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
    val state = viewModel.state.value
    val categories = state.categories
    var openDialog by remember { mutableStateOf(false) }
    var productId by remember { mutableStateOf(0) }
    val snackScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { message ->
            snackScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = context.getString(message.resourceString),
                    actionLabel =
                    if (message is ProductPromptMessage.ProductDeleted)
                        context.getString(R.string.action_undo)
                    else null
                )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.onEvent(DisplayProductsEvent.RestoreProduct)
                }
            }
        }
    }

    // TODO: 26/10/2022 !! asserts
    if (openDialog)
        OptionsDialog(
            modifier = Modifier,
            shouldOpenDialog = { openDialog = false },
            title = stringResource(id = R.string.choose_action),
            btn1Name = stringResource(id = R.string.action_edit),
            btn1Icon = Icons.Rounded.Edit,
            btn1OnClick = {
                openDialog = false
                navHostController.navigate(ProductsDirections.AddProduct.passArgument(productId))
            },
            btn2Name = stringResource(id = R.string.action_delete),
            btn2Icon = Icons.Rounded.Delete,
            btn2OnClick = {
                openDialog = false
                viewModel.onEvent(DisplayProductsEvent.DeleteProduct(productId))
            }
        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        items(
            items = categories
        ) { category ->
            Log.d("QWAS - DisplayProducts:", "Recomposition2")
            Spacer(modifier = Modifier.padding(4.dp))
            ExpandableCategoryCard(
                modifier = Modifier.fillMaxSize(),
                cardName = category.name,
                expandValue = category.isExpanded,
                backgroundColor = category.color,
                expandIconOnClick = {
                    viewModel.onEvent(DisplayProductsEvent.ExpandCategory(category))
                }
            ) {
                Log.d("QWAS - DisplayProducts:", "Recomposition3")
                val products2 = viewModel.state.value.productsOfCategory[category] ?: emptyList()
                Log.d("QWAS - DisplayProducts:", " PRODUCTS2 $products2")
                val products =
                    viewModel.getCategoriesProduct(category.id).collectAsStateLifecycleAware(
                        initial = emptyList()
                    ).value
                ProductItemsList(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(10.dp),
                    backgroundColor = category.color,
                    products = products2,
                    onItemClick = { product ->
                        viewModel.onEvent(
                            DisplayProductsEvent.ChangeProductSelection(product)
                        )
                    },
                    onLongClick = { product ->
                        productId = product.id
//                        viewModel.onEvent(DisplayProductsEvent.OnProductLongClick(product))
                        openDialog = true
                    }
                )
            }
        }
    }
}
