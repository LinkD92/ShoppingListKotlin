package com.symbol.shoppinglist.ui.categoriesManage

import androidx.lifecycle.ViewModel
import com.symbol.shoppinglist.database.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(private val repository: ListRepository) :
    ViewModel() {

}