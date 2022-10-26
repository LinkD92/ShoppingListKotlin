package com.symbol.shoppinglist.feature_product.presentation.display_products.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.symbol.shoppinglist.IconName
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor
import com.symbol.shoppinglist.core.presentation.ui.theme.Shapes
import com.symbol.shoppinglist.feature_product.domain.model.Product

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
        Box(
            modifier = Modifier
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .background(color = backgroundColor)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
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
    products: List<Product>,
    onItemClick: (Product) -> Unit,
    onLongClick: (Product) -> Unit
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
            var isCheckedState by remember(product.id) { mutableStateOf(product.isChecked) }
            val alphaValue = if (isCheckedState) 1f else 0.3f
            val itemBackgroundColor = Color(backgroundColor).copy(alphaValue)
            ProductItem(
                product = product,
                backgroundColor = itemBackgroundColor,
                onClick = {
                    isCheckedState = !isCheckedState
                    onItemClick(product)
                },
                onLongClick = { onLongClick(product) }
            )
        }
    }
}

@Composable
fun ExpandableCategoryCard(
    modifier: Modifier = Modifier,
    cardName: String,
    expandValue: Boolean,
    backgroundColor: Long,
    expandIconOnClick: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Log.d("QWAS - ExpandableCategoryCard:", "Recomposition1")
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = 10.dp
    ) {
        var expand by rememberSaveable { mutableStateOf(expandValue) }
        val rotateAngle = if (expand) 180f else 0f
        Column {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Log.d("QWAS - ExpandableCategoryCard:", "Recomposition2")
                Icon(Icons.Rounded.ExpandCircleDown, IconName.DROPDOWN,
                    tint = Color(backgroundColor),
                    modifier = Modifier
                        .clickable {
                            expand = !expand
                            expandIconOnClick(expand)
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