package com.symbol.shoppinglist.core.presentation.ui

import androidx.annotation.StringRes

sealed class UiPrompt{
    class StringResource(
        @StringRes val resId: Int
    ): UiPrompt()
}
