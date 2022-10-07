package com.symbol.shoppinglist.ui.productAdd

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.symbol.shoppinglist.database.entities.Category
import com.symbol.shoppinglist.ui.ColorSquare

@Composable
fun AddProduct(
    modifier: Modifier = Modifier,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories = viewModel.allCategories.observeAsState().value ?: listOf()

    Column {
        Log.d("QWAS - AddProduct:", "${categories.size}")
        LabelAndPlaceHolder(viewModel.productName) {
            viewModel.updateName(it)
        }
        CategoriesDropDown(modifier, categories) { viewModel.chooseCategory() }
        AddButton(modifier, onClick = { viewModel.addProduct() })
    }

    LaunchedEffect(true) {
        viewModel.successObserver.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun CategoriesDropDown(modifier: Modifier = Modifier, categories: List<Category>, chooseCategory: (String) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    DropdownMenu(
        modifier = modifier
            .width(100.dp)
            .height(100.dp),
        expanded = expanded
        , onDismissRequest = { expanded = false}) {
        categories.forEach { category ->  
            DropdownMenuItem(onClick = { expanded = false
                chooseCategory(category.categoryName)
            }) {
                Text(text = category.categoryName)
                ColorSquare(modifier, category)
            }
        }
    }
}

@Preview
@Composable
fun demoDrop(){
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                Text("Refresh")
            }
            DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                Text("Settings")
            }
            Divider()
            DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                Text("Send Feedback")
            }
        }
    }
}

@Composable
fun CategoryDropDownItem(modifier: Modifier = Modifier){

}