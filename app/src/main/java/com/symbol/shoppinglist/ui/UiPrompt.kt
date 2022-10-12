package com.symbol.shoppinglist.ui

import androidx.annotation.StringRes

sealed class UiPrompt{
    class StringResource(
        @StringRes val resId: Int
    ): UiPrompt()
}
