package com.example.demo.feature_xy.utils

import android.content.Context
import kotlin.math.roundToInt

fun Context.convertDpToPixel(dp: Int): Int {
    val density = applicationContext.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}