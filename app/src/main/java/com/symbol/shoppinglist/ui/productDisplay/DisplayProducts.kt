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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.ui.theme.MyColor
import com.symbol.shoppinglist.ui.theme.Shapes
import org.w3c.dom.Text

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DisplayProducts(
    navHostController: NavHostController = rememberNavController(),
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val list by viewModel.categoriesWithProducts.observeAsState()
    var openDialog = remember { mutableStateOf(false) }
    var productId by remember { mutableStateOf(0) }
    OptionsDialog(
        openDialog,
        stringResource(id = R.string.choose_action),
        stringResource(id = R.string.action_edit),
        Icons.Rounded.Edit,
        {
            openDialog.value = false
            navHostController.navigate(
                ProductsDirections.AddProduct.passArgument(
                    productId
                )
            )
        },
        stringResource(id = R.string.action_delete),
        Icons.Rounded.Delete,
        {
            viewModel.deleteProductById(productId)
            openDialog.value = false
        }
    )
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
                    mainAxisSpacing = 4.dp,
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
                                openDialog.value = true
                                productId = product.id
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    product: Product,
    categoryColor: Long,
    onClick: (Product) -> Unit,
    onLongPress: () -> Unit,
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
                    onLongClick = { onLongPress() }
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
                    modifier = Modifier.alpha(alphaValue),
                    textAlign = TextAlign.Start,
                    text = product.name
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

@Composable
fun OptionsDialog(
    shouldOpenDialog: MutableState<Boolean>,
    title: String,
    btn1Title: String,
    btn1Icon: ImageVector,
    btn1OnClick: () -> Unit,
    btn2Title: String,
    btn2Icon: ImageVector,
    btn2OnClick: () -> Unit
) {
    if (shouldOpenDialog.value)
        Dialog(
            onDismissRequest = { shouldOpenDialog.value = false },
        ) {
            Surface(shape = Shapes.medium) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { btn1OnClick() }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = btn1Title)
                                Icon(btn1Icon, null)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = { btn2OnClick() }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = btn2Title, textAlign = TextAlign.Center)
                                Icon(btn2Icon, null)
                            }
                        }
                    }
                }
            }
        }
}


