package com.stradivarius.charades.data.dto

import com.stradivarius.charades.data.common.Dto
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.ui.utils.toStringList
import org.json.JSONArray
import org.json.JSONObject

class MainDto(
    val categoriesMap: LinkedHashMap<String, List<String>> = linkedMapOf()
) : Dto {

    companion object {
        const val KEY_CATEGORY = "category"
        const val KEY_LIST = "list"

        private fun jsonArrayToLinkedMap(
            categoriesJSONArray: JSONArray
        ): LinkedHashMap<String, List<String>> {
            val linkedHashMap = linkedMapOf<String, List<String>>()
            for (i in 0 until categoriesJSONArray.length()) {
                categoriesJSONArray.getJSONObject(i).apply {
                    linkedHashMap[getString(KEY_CATEGORY)] = getJSONArray(KEY_LIST).toStringList()
                }
            }
            return linkedHashMap
        }
    }

    constructor(jsonArray: JSONArray) : this(
        jsonArrayToLinkedMap(jsonArray)
    )

    fun toJsonArray(): JSONArray {
        val result = JSONArray()
        for ((title, list) in categoriesMap.toList()) {
            JSONObject().apply {
                put(KEY_CATEGORY, title)
                put(KEY_LIST, JSONArray(list.toTypedArray()))
                result.put(this)
            }
        }
        return result
    }

}