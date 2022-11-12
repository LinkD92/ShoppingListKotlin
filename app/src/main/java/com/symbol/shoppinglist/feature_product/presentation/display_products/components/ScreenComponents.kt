package com.symbol.shoppinglist.feature_product.presentation.display_products.components

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.symbol.shoppinglist.core.data.util.Action
import com.symbol.shoppinglist.core.data.util.IconName
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_product.domain.model.Product
import com.symbol.shoppinglist.ui.collectAsStateLifecycleAware
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    backgroundColor: Color,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        elevation = 10.dp,
        shape = RoundedCornerShape(35)
    ) {
        Log.d("Recomposition - ProductItem:", "Recomposition 1 ${product.name}")
        Box(
            modifier = Modifier
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .background(color = backgroundColor)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Log.d("Recomposition - ProductItem:", "2")
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.alpha(backgroundColor.alpha),
                    textAlign = TextAlign.Start,
                    text = product.name
                )
            }
        }
    }
}

@Composable
fun ProductItemsList(
    modifier: Modifier = Modifier,
    backgroundColor: Long,
    products: List<StateFlow<Product>>,
    onItemClick: (Product) -> Unit,
    onLongClick: (Product) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        mainAxisSize = SizeMode.Wrap,
        mainAxisSpacing = 5.dp,
        mainAxisAlignment = MainAxisAlignment.SpaceEvenly,
        crossAxisSpacing = 10.dp,
        lastLineMainAxisAlignment = MainAxisAlignment.Start
    ) {
        // TODO: 26/10/2022 make it as content and take outside
        products.forEach { product ->
            Log.d("Recomposition - ProductItemsList:", "1")
            var isCheckedState by remember(product.value.id) {
                mutableStateOf(product.value.isChecked)
            }
            val alphaValue = if (product.value.isChecked) 1f else 0.3f
            val itemBackgroundColor = Color(backgroundColor).copy(alphaValue)
            ProductItem(
                product = product.value,
                backgroundColor = itemBackgroundColor,
                onClick = {
                    product.value.isChecked
                    onItemClick(product.value)
                },
                onLongClick = { onLongClick(product.value) }
            )
        }
    }
}

@Composable
fun ExpandableCategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    expandIconOnClick: (Boolean) -> Unit,
    elevation: Dp = 10.dp,
    categoryCardMenu: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Log.d("Recomposition - ExpandableCategoryCard:", "Recomposition1")
    var expand by remember { mutableStateOf(category.isExpanded) }
    var menuExpand by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = elevation,
    ) {
        val rotateAngle = if (expand) 180f else 0f
        Column() {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Log.d("Recomposition - ExpandableCategoryCard:", "Recomposition2")
                Icon(Icons.Rounded.ExpandCircleDown, IconName.DROPDOWN,
                    tint = Color(category.color),
                    modifier = Modifier
                        .clickable {
                            expand = !expand
                            expandIconOnClick(expand)
                        }
                        .rotate(rotateAngle)
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = category.name,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                )
                Column() {
                    Icon(Icons.Rounded.MoreVert, Action.ADD, modifier = Modifier.clickable {
                        menuExpand = !menuExpand
                    })
                    DropdownMenu(
                        modifier = Modifier,
                        expanded = menuExpand,
                        onDismissRequest = { menuExpand = !menuExpand }
                    ){
                        categoryCardMenu()
                    }
                }
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