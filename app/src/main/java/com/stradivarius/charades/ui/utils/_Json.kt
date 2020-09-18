package com.stradivarius.charades.ui.utils

import org.json.JSONArray

fun JSONArray?.toStringList(): List<String> {
    val list = mutableListOf<String>()
    if (this == null) {
        return list
    }
    for (i in 0 until this.length()) {
        list.add(this.optString(i))
    }
    return list
}