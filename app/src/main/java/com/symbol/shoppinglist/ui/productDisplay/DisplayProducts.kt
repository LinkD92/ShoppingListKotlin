package com.symbol.shoppinglist.ui.productDisplay

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.database.local.entities.Product
import com.symbol.shoppinglist.navigation.ProductsDirections
import com.symbol.shoppinglist.ui.collectAsStateLifecycleAware
import com.symbol.shoppinglist.ui.theme.MyColor
import com.symbol.shoppinglist.ui.theme.Shapes
import kotlinx.coroutines.flow.Flow

@Composable
fun DisplayProducts(
    navHostController: NavHostController = rememberNavController(),
    viewModel: DisplayProductViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsStateLifecycleAware(initial = emptyList())
    var openDialog by remember { mutableStateOf(false) }
    var productId by remember { mutableStateOf(0) }
    if (openDialog)
        OptionsDialog(
            { openDialog = false },
            stringResource(id = R.string.choose_action),
            stringResource(id = R.string.action_edit),
            Icons.Rounded.Edit,
            {
                openDialog = false
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
                openDialog = false
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
            Spacer(modifier = Modifier.padding(4.dp))
            ExpandableCategoryCard(
                category.name,
                category.isExpanded,
                category.color,
                { isExpanded ->
                    viewModel.changeCategoryExpand(category, isExpanded)
                }
            ) {
                val products by viewModel.getCategoriesProduct(category.id).collectAsStateLifecycleAware(
                    initial = emptyList()
                )
                ProductItemsList(
                    categoryColor = category.color,
                    productsFlow = products,
                    onItemClick = { product -> viewModel.updateProduct(product) },
                    onLongClick = { openDialog = true }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    product: Product,
    categoryColor: Color,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Surface(
        modifier = Modifier,
        elevation = 10.dp,
        shape = RoundedCornerShape(35)
    ) {
        Box(
            modifier = Modifier
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .background(color = categoryColor)
                .padding(horizontal = 10.dp, vertical = 2.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.alpha(categoryColor.alpha),
                    textAlign = TextAlign.Start,
                    text = product.name
                )
            }
        }
    }
}

@Composable
fun ProductItemsList(
    categoryColor: Long,
    productsFlow: List<Product>,
    onItemClick: (Product) -> Unit,
    onLongClick: () -> Unit
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
        productsFlow.forEach { product ->
            var isCheckedState by remember(product.id) { mutableStateOf(product.isChecked) }
            val alphaValue = if (isCheckedState) 1f else 0.3f
            val backgroundColor = Color(categoryColor).copy(alphaValue)
            ProductItem(
                product = product,
                categoryColor = backgroundColor,
                onClick = {
                    isCheckedState = !isCheckedState
                    onItemClick(product.apply { isChecked = isCheckedState })
                },
                onLongClick = onLongClick
            )
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
    shouldOpenDialog: () -> Unit,
    title: String,
    btn1Title: String,
    btn1Icon: ImageVector,
    btn1OnClick: () -> Unit,
    btn2Title: String,
    btn2Icon: ImageVector,
    btn2OnClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { shouldOpenDialog() },
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
