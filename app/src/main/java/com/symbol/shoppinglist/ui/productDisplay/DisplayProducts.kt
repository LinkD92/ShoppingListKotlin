package com.symbol.shoppinglist.ui.productDisplay

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.database.local.entities.Category
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.database.local.entities.relations.CategoryWithProducts
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.ui.theme.MyColor
import com.symbol.shoppinglist.ui.theme.ShoppingListTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts(
    navHostController: NavHostController = rememberNavController(),
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val list by viewModel.categoriesWithProducts.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        items(
            items = list ?: listOf()
        ) { categoryWithProduct ->
            Spacer(modifier = Modifier.padding(4.dp))
            ExpandableCategoryCard(
                categoryWithProduct.category.name,
                categoryWithProduct.category.isExpanded,
                categoryWithProduct.category.color,
                { isExpanded ->
                    viewModel.changeCategoryExpand(categoryWithProduct.category, isExpanded)
                }
            ) {
                FlowRow(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(10.dp),
                    mainAxisSize = SizeMode.Wrap,
                    mainAxisSpacing = 2.dp,
                    mainAxisAlignment = MainAxisAlignment.SpaceEvenly,
                    crossAxisSpacing = 10.dp,
                    lastLineMainAxisAlignment = MainAxisAlignment.Start
                ) {
                    categoryWithProduct.products.forEach { product ->
                        ProductItem(
                            product = product,
                            categoryColor = categoryWithProduct.category.color,
                            onClick = { viewModel.updateProduct(product) },
                            onLongPress = {
                                navHostController.navigate(
                                    ProductsDirections.AddProduct.passArgument(
                                        product.id
                                    )
                                )
                            },
                            deleteProduct = { viewModel.deleteProduct(product) })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    val category = Category("test", 1999922222, true, 1)
    val product = Product("test1", 1, true, 0, 1)
    val product1 = Product("test1test1t", 1, true, 0, 1)
    val product2 = Product("test1test1test1asdadstest1", 1, true, 0, 1)
    val product3 = Product("test1test1test1test1", 1, true, 0, 1)
    val list = mutableListOf<Product>()
    list.add(product)
    list.add(product1)
    list.add(product2)
    list.add(product3)

    val categoryWithProduct = CategoryWithProducts(category, list)

    FlowRow(
        modifier = Modifier
            .width(IntrinsicSize.Min),
        mainAxisSize = SizeMode.Wrap,
        mainAxisAlignment = MainAxisAlignment.SpaceAround,
        crossAxisSpacing = 5.dp,
        lastLineMainAxisAlignment = MainAxisAlignment.Start
    ) {
        categoryWithProduct.products.forEach { product ->
            ProductItem(
                product = product,
                categoryColor = categoryWithProduct.category.color,
                onClick = { },
                onLongPress = {

                },
                deleteProduct = { })
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    product: Product,
    categoryColor: Long,
    onClick: (Product) -> Unit,
    onLongPress: (Product) -> Unit,
    deleteProduct: (Product) -> Unit,
) {
    var isChecked by remember { mutableStateOf(product.isChecked) }
    val alphaValue = if (isChecked) 1f else 0.3f
    val backgroundColor = Color(categoryColor).copy(alphaValue)
    Surface(
        modifier = Modifier,
        elevation = 10.dp,
        shape = RoundedCornerShape(35)
    ) {
        Box(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        isChecked = !isChecked
                        onClick(product.apply { this.isChecked = isChecked })
                    },
                    onLongClick = { onLongPress(product) }
                )
                .background(color = backgroundColor)
                .padding(horizontal = 10.dp, vertical = 2.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Start,
                    text = product.name
                )
                Icon(
                    Icons.Rounded.Delete, IconName.DELETE,
                    modifier = Modifier
                        .clickable { deleteProduct(product) }
                )
            }
        }
    }
}

@Composable
fun ExpandableCategoryCard(
    cardName: String,
    expandValue: Boolean,
    categoryColor: Long,
    changeExpand: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = 10.dp
        ) {
            var expand by rememberSaveable {
                mutableStateOf(expandValue)
            }
            val rotateAngle = if (expand) 180f else 0f
            Column {
                Row(
                    modifier = Modifier.padding(2.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Rounded.ExpandCircleDown, IconName.DROPDOWN,
                        tint = Color(categoryColor),
                        modifier = Modifier
                            .clickable {
                                expand = !expand
                                changeExpand(expand)
                            }
                            .rotate(rotateAngle)
                            .padding(5.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = cardName,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                    )
                }
                if (expand) {
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(0.95f)
                            .height(1.dp)
                            .background(MyColor.OnSurface)
                    )
                    content()
                }
            }
        }
}


