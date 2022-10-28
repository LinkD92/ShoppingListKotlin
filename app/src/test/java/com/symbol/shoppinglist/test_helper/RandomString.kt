package com.symbol.shoppinglist.test_helper

import kotlin.random.Random

class TestHelper {
    fun randomStringByKotlinRandom() = (1..STRING_LENGTH)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")
}