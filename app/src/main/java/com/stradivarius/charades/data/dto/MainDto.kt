package com.stradivarius.charades.data.dto

import com.stradivarius.charades.data.common.Dto
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.data.repository.local.LocalStorageImpl
import org.json.JSONArray
import org.json.JSONObject

class MainDto(
    val categoriesJson: JSONObject = JSONObject()
) : Dto {

    fun toModel(): MainModel {
       return MainModel(jsonToList(categoriesJson))
    }

    private fun jsonToList(json: JSONObject): List<Pair<String, List<String>>> {
        val pairList: MutableList<Pair<String, List<String>>> = mutableListOf()
        val categoriesJSONArray = json.getJSONArray(LocalStorageImpl.KEY_CATEGORIES)
        for (i in 0 until categoriesJSONArray.length()) {
            val category = categoriesJSONArray.getJSONObject(i)
            pairList.add(
                Pair(category.optString(LocalStorageImpl.KEY_CATEGORY),
                    category.optJSONArray(LocalStorageImpl.KEY_LIST).toList())
            )
        }
        return pairList
    }

    private fun JSONArray?.toList(): List<String> {
        val list = mutableListOf<String>()
        if (this == null) {
            return list
        }
        for (i in 0 until this.length()) {
            list.add(this.optString(i))
        }
        return list
    }

}