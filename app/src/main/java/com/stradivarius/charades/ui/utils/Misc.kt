package com.stradivarius.charades.ui.utils

import kotlin.math.roundToInt

fun Int.toRadians(): Double {
    return this*(Math.PI/180)
}

fun Long.formatForDisplay(): String {
    return (this.toDouble() / 1000).roundToInt().toString()
}
