package com.example.myapplication.utils

interface ScrollViewListener {
    fun onScrollChanged(
        scrollView: MyScrollView?,
        x: Int, y: Int, oldx: Int, oldy: Int
    )
}