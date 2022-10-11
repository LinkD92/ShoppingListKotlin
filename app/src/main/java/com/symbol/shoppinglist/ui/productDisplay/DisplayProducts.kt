package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.ui.productDisplay.DisplayProductViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val list by viewModel.categoriesWithProducts.observeAsState()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(
            items = list ?: listOf()
        ) { categoryWithProduct ->
            ExpandableCategoryCard(
                modifier,
                categoryWithProduct.category.categoryName,
                categoryWithProduct.category.isExpanded,
                { isExpanded ->
                    viewModel.changeCategoryExpand(categoryWithProduct.category, isExpanded)
                }
            ) {
                StaggeredGrid {
                    categoryWithProduct.products.forEach { product ->
                        ProductItem(
                            product = product,
                            categoryColor = categoryWithProduct.category.categoryColor,
                            onClick = { viewModel.updateProduct(product) },
                            onLongPress = {
                                navHostController.navigate(ProductsDirections.AddProduct.passProductName(product.productName))
                            },
                            deleteProduct = { viewModel.deleteProduct(product) })
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    categoryColor: Long,
    onClick: (Product) -> Unit,
    onLongPress: (Product) -> Unit,
    deleteProduct: (Product) -> Unit,
) {
    var isChecked by remember { mutableStateOf(product.isProductChecked) }
    val alphaValue = if (isChecked) 1f else 0.3f
    val backgroundColor = Color(categoryColor).copy(alphaValue)
    Surface(
        modifier = modifier,
        elevation = 10.dp,
    ) {
        Box(
            modifier = modifier
                .combinedClickable(
                    onClick = {
                        isChecked = !isChecked
                        onClick(product.apply { isProductChecked = isChecked })
                    },
                    onLongClick = { onLongPress(product) }
                )
                .background(color = backgroundColor)
        ) {
            Row(
                modifier = modifier
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
    cardName: String,
    expandValue: Boolean,
    changeExpand: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 10.dp
    ) {
        var expand by rememberSaveable {
            mutableStateOf(expandValue)
        }
        Column() {
            Row {
                Text(text = cardName)
                Icon(Icons.Rounded.ArrowDropDown, IconName.DROPDOWN,
                    modifier = modifier
                        .clickable {
                            expand = !expand
                            changeExpand(expand)
                        })
            }
            if (expand) {

                content()
            }
        }
    }
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    children: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = children
    ) { measurables, constraints ->
        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowMaxHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->

            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] = rowWidths[row] + placeable.width
            rowMaxHeights[row] = kotlin.math.max(rowMaxHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowMaxHeights.sumBy { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowMaxHeights[i - 1]
        }
        // Set the size of the parent layout
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}


