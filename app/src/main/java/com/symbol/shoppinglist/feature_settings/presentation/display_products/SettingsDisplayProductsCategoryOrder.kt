package com.symbol.shoppinglist.feature_settings.presentation.display_products

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Reorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = stringResource(id = R.string.sort_type))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButtonWithDescription(
                isSelected = (state.value.sortType == SortType.ASCENDING),
                onClick = { viewModel.onEvent(SettingsDisplayProductEvent.ChangeSortType(SortType.ASCENDING)) },
                description = stringResource(id = R.string.sort_type_asc)
            )
            Spacer(modifier = Modifier.width(5.dp))
            RadioButtonWithDescription(
                isSelected = (state.value.sortType == SortType.DESCENDING),
                onClick = {
                    viewModel.onEvent(
                        SettingsDisplayProductEvent.ChangeSortType(SortType.DESCENDING)
                    )
                },
                description = stringResource(id = R.string.sort_type_desc)
            )
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
            isSelected = (state.value.categoryOrderType == CategoryOrderType.NAME),
            onClick = {
                viewModel.onEvent(
                    SettingsDisplayProductEvent.ChangeOrderType(CategoryOrderType.NAME)
                )
            },
            description = stringResource(id = R.string.order_type_name)
        )
        Row() {
            RadioButtonWithDescription(
                isSelected = (state.value.categoryOrderType == CategoryOrderType.CUSTOM),
                onClick = {
                    viewModel.onEvent(
                        SettingsDisplayProductEvent.ChangeOrderType(CategoryOrderType.CUSTOM)
                    )
                },
                description = stringResource(id = R.string.order_type_custom)
            )
            Button(
                onClick = { showCustomOrderView = !showCustomOrderView },
                enabled = (state.value.categoryOrderType == CategoryOrderType.CUSTOM)
            ) {
                Text(text = stringResource(id = R.string.order_type_custom_change))
            }
        }

    }
    if (showCustomOrderView) {
        CustomOrderView(
            categories = state.value.categories,
            onCancelClick = { showCustomOrderView = !showCustomOrderView },
            onSaveClick = { categories ->
                viewModel.onEvent(
                    SettingsDisplayProductEvent.SaveCustomOrderSettings(categories)
                )
            }
        )
    }
}

@Composable
fun RadioButtonWithDescription(
    isSelected: Boolean,
    onClick: () -> Unit,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = description, Modifier.padding(horizontal = 10.dp))
    }
}

@Composable
fun CustomOrderView(
    categories: List<Category>,
    onCancelClick: () -> Unit,
    onSaveClick: (List<Category>) -> Unit
) {
    var categoryState by remember {
        mutableStateOf(categories)
    }
    val reorderState = rememberReorderableLazyListState(onMove = { from, to ->
        categoryState = categoryState.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.7f))
            .clickable(enabled = false) { onCancelClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val constraints = ConstraintSet {
            val categoriesLazyColumn = createRefFor("categoriesLazyColumn")
            val buttonsRow = createRefFor("buttonsRow")
            constrain(categoriesLazyColumn) {
                top.linkTo(parent.top)
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
            LazyColumn(
                state = reorderState.listState,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .reorderable(reorderState)
                    .detectReorderAfterLongPress(reorderState)
                    .fillMaxHeight()
                    .layoutId("categoriesLazyColumn")
            ) {
                items(categoryState, key = { it.id }) { category ->
                    Spacer(modifier = Modifier.padding(4.dp))
                    ReorderableItem(reorderState, key = category.id) { isDragging ->
                        val elevation = animateDpAsState(if (isDragging) 26.dp else 0.dp)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            CategoryItem(
                                modifier = Modifier
                                    .shadow(elevation.value)
                                    .background(
                                        shape = Shapes.medium,
                                        color = Color(category.color)
                                    ),
                                category = category
                            )
                            Icon(Icons.Rounded.Reorder, null)
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.layoutId("buttonsRow")
            ) {
                Button(onClick = {
                    onSaveClick(categoryState.apply {
                        forEachIndexed { index, category ->
                            category.customOrder = index
                        }
                    })
                }) {
                    Text(text = "Save")
                }
                Button(onClick = { onCancelClick() }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}























