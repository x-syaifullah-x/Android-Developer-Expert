package com.android.developer.expert.core

import org.junit.Test

class ExampleUnitTest {
    @Test
    fun test() {
        println(1 % 2)

        val a = 20.rangeTo(500 * 20).filter { it % 20 == 0 }
        println("$a")
    }
}