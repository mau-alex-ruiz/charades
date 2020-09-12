package com.stradivarius.charades.data.repository.main.local

import android.content.res.AssetManager
import android.util.Log
import com.stradivarius.charades.data.model.MainModel
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class MainLocalImpl(
    private val assetManager: AssetManager
) : MainLocal {

    override fun getCategories(): MainModel {
        var jsonString = ""
        try {
            val jsonInputStream = assetManager.open("categories.json")
            val size = jsonInputStream.available()
            val buffer = ByteArray(size)
            jsonInputStream.read(buffer)
            jsonInputStream.close()
            jsonString = String(buffer, Charset.defaultCharset())
        } catch (e: Exception) {
            Log.e("${this::class}", "Unable to fetch categories from json")
        }
        val categoryList = JSONObject(jsonString).getJSONArray("categories")
        val pairList: MutableList<Pair<String, List<String>>> = mutableListOf()
        for (i in 0 until categoryList.length()) {
            val category = categoryList.getJSONObject(i)
            pairList.add(
                Pair(category.optString("category"),
                    category.optJSONArray("list").toList())
            )
        }
        return MainModel(pairList)
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

    override fun close() {
        //no-op
    }

}