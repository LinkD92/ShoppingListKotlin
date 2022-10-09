package com.symbol.shoppinglist.product

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.database.entities.Product
import com.symbol.shoppinglist.database.entities.relations.CategoryWithProducts
import com.symbol.shoppinglist.ui.productDisplay.DisplayProductViewModel
import kotlin.math.exp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts(
    modifier: Modifier = Modifier,
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val mylist = remember { mutableStateListOf<CategoryWithProducts>().apply { addAll(viewModel.list.value) } }
    LazyColumn(modifier = modifier) {
        items(
            items = mylist,
            key = { item -> item.category.categoryName }
        ) { item ->
                LazyColumn(content = modifier){
                    items(
                        items = item,
                        key = { item -> item}

                }
            }
        }
    }
}

//    Log.d("QWAS - DisplayProducts:", "${viewModel.expand}")
//    Column() {
//        Text(text = viewModel.list.value.size.toString())
//        viewModel.list.value.forEach { categoryWithProducts ->
////            ExpandableCategoryCard(
////                modifier,
////                categoryWithProducts,
////                onClick = { product -> viewModel.updateProduct(product) },
////                onIconRemoveClick = { product -> viewModel.deleteProduct(product) },
////                expand = viewModel.expand,
////                { viewModel.changeExpand() }
////            )
//            categoryWithProducts.products.forEach { product ->
//                ProductItem(
//                    product = product,
//                    categoryColor = categoryWithProducts.category.categoryColor,
//                    onClick = { item -> viewModel.updateProduct(item) },
//                    deleteProduct = { item -> viewModel.deleteProduct(item) })
//            }
//        }
            
//            Text(
//                text = categoryWithProducts.category.categoryName,
//                fontSize = 30.sp
//            )
//            categoryWithProducts.products.forEach { product ->
//                ProductItem(
//                    modifier,
//                    product,
//                    categoryWithProducts.category.categoryColor,
//                    { product -> viewModel.updateProduct(product) },
//                    { product -> viewModel.deleteProduct(product) }
//                )
//
//            }     }

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    categoryColor: Long,
    onClick: (Product) -> Unit,
    deleteProduct: (Product) -> Unit,
) {
    val colorTransparency = if (product.isProductChecked)
        Color(categoryColor).copy(alpha = 1f)
    else
        Color(categoryColor).copy(alpha = 0.3f)
    Surface(
        modifier = modifier,
        elevation = 10.dp,
    ) {
        Log.d("QWAS - ProductItem:", "PRODUCT")
        Box(
            modifier = modifier
                .clickable {
                    onClick(product)
                }
                .background(color = colorTransparency)
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
    Log.d("QWAS - ExpandableCategoryCard:", "$expand")
    Card(
        modifier = modifier
            .background(color = Color(categoryWithProducts.category.categoryColor).copy()),
        elevation = 10.dp,
    ) {
        Column() {
            Row(modifier) {
                Text(text = categoryWithProducts.category.categoryName)
                Icon(Icons.Rounded.ArrowDropDown, IconName.EXPAND,
                    modifier.clickable { changeExpand()})
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
                    modifier.clickable {expand = !expand })
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



