package com.symbol.shoppinglist.core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.symbol.shoppinglist.core.domain.MainActivityActionEvent
import com.symbol.shoppinglist.core.presentation.MainActivityViewModel
import com.symbol.shoppinglist.core.presentation.ShoppingListApp
import com.symbol.shoppinglist.feature_product.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    val products = emptyList<Product>()

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        Log.d("QWAS:", "18:MainActivity -> $uri")
        // Handle the returned Uri
    }
    val activityForResultContracts =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { activityResult ->
            saveToJson(activityResult?.data?.data)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListApp(::activityActionEvent)
        }
    }

    private fun activityActionEvent(event: MainActivityActionEvent): ActivityResultLauncher<Intent>? {
        when (event) {
            is MainActivityActionEvent.CreateFileAction -> test()
        }
        return null
    }

    private fun test() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, "list_export.json")
        }

        activityForResultContracts.launch(intent)
        Log.d("QWAS:", "63:test -> poszlo")
    }

    private fun saveToJson(path: Uri?) {
        val correctPath = path?.path
        if (correctPath != null) {
            lifecycleScope.launch{
                viewModel.products.collect{
                    val test = contentResolver.openOutputStream(path)
                    val gson = Gson()
                    Log.d("QWAS:", "74:saveToJson -> $it")
                    test?.write(gson.toJson(it).toByteArray())
                    test?.close()
                }
            }
        }
    }

}