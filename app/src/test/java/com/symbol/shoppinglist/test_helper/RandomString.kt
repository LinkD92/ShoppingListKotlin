package com.symbol.shoppinglist.test_helper

import kotlin.random.Random

class RandomString() {
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    operator fun invoke(stringLength: Int) = (1..stringLength)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")
}