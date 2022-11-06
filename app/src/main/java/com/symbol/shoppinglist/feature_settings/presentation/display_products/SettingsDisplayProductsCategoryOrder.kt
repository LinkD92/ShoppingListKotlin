package com.symbol.shoppinglist.feature_settings.presentation.display_products

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Reorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.symbol.shoppinglist.R
import com.symbol.shoppinglist.core.presentation.components.CustomButton
import com.symbol.shoppinglist.core.presentation.components.CustomViewHeader
import com.symbol.shoppinglist.core.presentation.components.RadioButtonWithDescription
import com.symbol.shoppinglist.core.presentation.ui.theme.MyColor
import com.symbol.shoppinglist.core.presentation.ui.theme.Shapes
import com.symbol.shoppinglist.feature_category.domain.model.Category
import com.symbol.shoppinglist.feature_category.domain.util.CategoryOrderType
import com.symbol.shoppinglist.feature_category.domain.util.SortType
import com.symbol.shoppinglist.feature_category.presentation.manage_categories.components.CategoryItem
import com.symbol.shoppinglist.feature_settings.presentation.settings.SettingsViewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun SettingsDisplayProductsCategoryOrder(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var showCustomOrderView by rememberSaveable {
        mutableStateOf(false)
    }
    RadioButtonsSection(
        currentSortType = state.value.sortType,
        changeSortType = { sortType ->
            viewModel.onEvent(SettingsDisplayProductEvent.ChangeSortType(sortType))
        },
        showCustomOrderView = { showCustomOrderView = !showCustomOrderView },
        currentOrderType = state.value.categoryOrderType,
        changeOrderType = { categoryOrderType ->
            viewModel.onEvent(SettingsDisplayProductEvent.ChangeOrderType(categoryOrderType))
        }
    )
    if (showCustomOrderView) {
        CustomSortView(
            categories = state.value.categories,
            onCancelClick = { showCustomOrderView = !showCustomOrderView },
            onSaveClick = { categories ->
                viewModel.onEvent(SettingsDisplayProductEvent.SaveCustomOrderSettings(categories))
                showCustomOrderView = !showCustomOrderView
            }
        )
    }
}

@Composable
fun CustomSortView(
    categories: List<Category>,
    onCancelClick: () -> Unit,
    onSaveClick: (List<Category>) -> Unit
) {
    var categoriesState by remember {
        mutableStateOf(categories)
    }
    val reorderState = rememberReorderableLazyListState(onMove = { from, to ->
        categoriesState = categoriesState.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.7f))
            .clickable(enabled = false) { onCancelClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val constraints = ConstraintSet {
            val categoriesLazyColumn = createRefFor(ViewId.LIST_ID)
            val buttonsRow = createRefFor(ViewId.BUTTONS_ID)
            val header = createRefFor(ViewId.HEADER_ID)
            constrain(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(categoriesLazyColumn.top)
                height = Dimension.wrapContent
                width = Dimension.matchParent
            }
            constrain(categoriesLazyColumn) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(buttonsRow.top)
                height = Dimension.fillToConstraints
                width = Dimension.matchParent
            }
            constrain(buttonsRow) {
                top.linkTo(categoriesLazyColumn.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.wrapContent
                width = Dimension.matchParent
            }
        }
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .fillMaxSize(0.8f)
                .padding(10.dp)
                .background(color = MyColor.Background, shape = Shapes.medium)
        ) {
            CustomViewHeader(
                headerTitle = stringResource(id = R.string.category_reorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId(ViewId.HEADER_ID)
            )
            LazyColumn(
                state = reorderState.listState,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .reorderable(reorderState)
                    .detectReorderAfterLongPress(reorderState)
                    .fillMaxHeight()
                    .layoutId(ViewId.LIST_ID)
            ) {
                items(categoriesState, key = { it.id }) { category ->
                    Spacer(modifier = Modifier.padding(4.dp))
                    ReorderableItem(reorderState, key = category.id) { isDragging ->
                        val elevation = animateDpAsState(if (isDragging) 26.dp else 0.dp)
                        val borderColor = if (isDragging) MyColor.Primary else Color(category.color)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            CategoryItem(
                                modifier = Modifier
                                    .background(
                                        shape = Shapes.medium,
                                        color = Color(category.color)
                                    ),
                                elevation = elevation.value,
                                borderColor = borderColor,
                                category = category
                            ) {
                                Icon(Icons.Rounded.Reorder, null)
                            }
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .layoutId(ViewId.BUTTONS_ID)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                CustomButton(
                    onClick = { onSaveClick(categoriesState) },
                    icon = Icons.Rounded.Check
                )
                CustomButton(
                    onClick = { onCancelClick() },
                    icon = Icons.Rounded.Close
                )
            }
        }
    }
}

@Composable
fun RadioButtonsSection(
    currentSortType: SortType,
    changeSortType: (SortType) -> Unit,
    showCustomOrderView: () -> Unit,
    currentOrderType: CategoryOrderType,
    changeOrderType: (CategoryOrderType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = stringResource(id = R.string.sort_type))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            RadioButtonWithDescription(
                isSelected = (currentSortType == SortType.ASCENDING),
                onClick = { changeSortType(SortType.ASCENDING) },
                description = stringResource(id = R.string.sort_type_asc)
            )
            Spacer(modifier = Modifier.height(5.dp))
            RadioButtonWithDescription(
                isSelected = (currentSortType == SortType.DESCENDING),
                onClick = { changeSortType(SortType.DESCENDING) },
                description = stringResource(id = R.string.sort_type_desc)
            )
            Row() {
                RadioButtonWithDescription(
                    isSelected = (currentSortType == SortType.CUSTOM),
                    onClick = { changeSortType(SortType.CUSTOM) },
                    description = stringResource(id = R.string.sort_type_custom)
                )
                CustomButton(
                    enabled = (currentSortType == SortType.CUSTOM),
                    onClick = showCustomOrderView,
                    text = stringResource(id = R.string.action_edit),
                    icon = Icons.Rounded.Edit
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .height(1.dp)
                .background(color = Color.Black.copy(alpha = 0.6f))
        )
        Text(text = stringResource(id = R.string.order_type))
        RadioButtonWithDescription(
            isSelected = (currentOrderType == CategoryOrderType.NAME),
            enabled = (currentSortType != SortType.CUSTOM),
            onClick = {
                changeOrderType(CategoryOrderType.NAME)
            },
            description = stringResource(id = R.string.order_type_name)
        )
    }
}


private object ViewId {
    const val HEADER_ID = "headerId"
    const val BUTTONS_ID = "buttonsID"
    const val LIST_ID = "listId"

}