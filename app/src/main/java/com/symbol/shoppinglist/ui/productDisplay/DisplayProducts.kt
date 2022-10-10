package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.database.entities.relations.CategoryWithProducts
import com.symbol.shoppinglist.ui.productDisplay.DisplayProductViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts(
    modifier: Modifier = Modifier,
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val list by viewModel.categoriesWithProducts.observeAsState()
    LazyColumn(modifier = modifier) {
        items(
            items = list ?: listOf()
        ) { categoryWithProduct ->
            var expand by rememberSaveable {
                mutableStateOf(categoryWithProduct.category.isExpanded)
            }
            Row {
                Text(text = categoryWithProduct.category.categoryName)
                Icon(Icons.Rounded.ArrowDropDown, IconName.DROPDOWN,
                    modifier = modifier
                        .clickable {
                            expand = !expand
                            viewModel.updateCategory(categoryWithProduct.category.apply {
                                isExpanded = expand
                            })
                        })
            }
            if (expand) {
                categoryWithProduct.products.forEach { product ->
                    ProductItem(
                        product = product,
                        categoryColor = categoryWithProduct.category.categoryColor,
                        onClick = { viewModel.updateProduct(product) },
                        deleteProduct = { viewModel.deleteProduct(product) })
                }
            }
        }
    }
}


@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    categoryColor: Long,
    onClick: (Product) -> Unit,
    deleteProduct: (Product) -> Unit,
) {
    var isChecked by remember { mutableStateOf(product.isProductChecked) }
    val backgroundColor = if (isChecked)
        Color(categoryColor).copy(alpha = 1f)
    else
        Color(categoryColor).copy(alpha = 0.3f)
    Surface(
        modifier = modifier,
        elevation = 10.dp,
    ) {
        Log.d("QWAS - ProductItem:", "${product.productName}")
        Box(
            modifier = modifier
                .clickable {
                    isChecked = !isChecked
                    onClick(product.apply { isProductChecked = isChecked })
                }
                .background(color = backgroundColor)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = product.productName)
                Icon(
                    Icons.Rounded.Delete, IconName.DELETE,
                    modifier = modifier.clickable { deleteProduct(product) }
                )
            }
        }
    }
}

@Composable
fun ExpandableCategoryCard(
    modifier: Modifier = Modifier,
    categoryWithProducts: CategoryWithProducts,
    onClick: (Product) -> Unit,
    onIconRemoveClick: (Product) -> Unit,
    expand: Boolean,
    changeExpand: () -> Unit
) {
    Card(
        modifier = modifier
            .background(color = Color(categoryWithProducts.category.categoryColor).copy()),
        elevation = 10.dp,
    ) {
        Column() {
            Row(modifier) {
                Text(text = categoryWithProducts.category.categoryName)
                Icon(Icons.Rounded.ArrowDropDown, IconName.EXPAND,
                    modifier.clickable { changeExpand() })
            }
            if (expand) {
                Row() {
                    categoryWithProducts.products.forEach { product ->
                        ProductItem(
                            modifier,
                            product,
                            categoryWithProducts.category.categoryColor,
                            onClick,
                            onIconRemoveClick
                        )
                    }
                }
            }
        }

    }
}


@Composable
fun ExpandableCategoryCardTest(
    modifier: Modifier = Modifier,
    categoryWithProducts: CategoryWithProducts,
    content: @Composable RowScope.() -> Unit
) {
    var expand by remember { mutableStateOf(false) }
    Log.d("QWAS - ExpandableCategoryCardTest:", "$expand")
    Card(
        modifier = modifier
            .background(color = Color(categoryWithProducts.category.categoryColor).copy()),
        elevation = 10.dp,
    ) {
        Column() {
            Row(modifier) {
                Text(text = categoryWithProducts.category.categoryName)
                Icon(Icons.Rounded.ArrowDropDown, IconName.EXPAND,
                    modifier.clickable { expand = !expand })
            }
            if (expand) {
//                Row(content = content)
            }
        }
    }
}

@Composable
fun test(
    modifier: Modifier = Modifier,
    categoryWithProducts: CategoryWithProducts,
    onClick: (Product) -> Unit,
    onIconRemoveClick: (Product) -> Unit
) {
    Row() {
        categoryWithProducts.products.forEach { product ->
            Log.d("QWAS - ExpandableCategoryCard:", "${product.productName}")
            ProductItem(
                modifier,
                product,
                categoryWithProducts.category.categoryColor,
                onClick,
                onIconRemoveClick
            )
        }
    }

}



