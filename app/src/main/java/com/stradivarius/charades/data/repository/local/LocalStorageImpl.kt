package com.stradivarius.charades.data.repository.local

import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stradivarius.charades.data.model.MainModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


class LocalStorageImpl(
    private val assetManager: AssetManager
) : LocalStorage {

    companion object {
        const val KEY_CATEGORIES = "categories"
        const val KEY_CATEGORY = "category"
        const val KEY_LIST = "list"
        const val CATEGORIES_FILE = "categories.json"
    }

    private var categoriesJson = JSONObject()
    private val mainModel = MutableLiveData<MainModel>()

    override fun getCategories(): LiveData<MainModel> {
        var jsonString = ""
        val file = getCategoriesFile()
        if (file.length().toInt() == 0) {
            try {
                val jsonInputStream = assetManager.open(CATEGORIES_FILE)
                val size = jsonInputStream.available()
                val buffer = ByteArray(size)
                jsonInputStream.read(buffer)
                jsonInputStream.close()
                jsonString = String(buffer, Charset.defaultCharset())
            } catch (e: Exception) {
                Log.e("${this::class}", "Unable to fetch categories from asset json")
            }
            BufferedWriter(FileWriter(file)).apply {
                write(jsonString)
                close()
            }
        } else {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            val stringBuilder = StringBuilder()
            var line = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
            jsonString = stringBuilder.toString()
        }
        categoriesJson = JSONObject(jsonString)
        mainModel.postValue(jsonStringToMainModel())
        return mainModel
    }

    override fun addCategory(title: String, list: String): Boolean {
        try {
            JSONObject().also { jsonObject ->
                jsonObject.put(KEY_CATEGORY, title)
                jsonObject.put(KEY_LIST, JSONArray(list.split(',').toTypedArray()))
                categoriesJson.getJSONArray(KEY_CATEGORIES).put(jsonObject)
            }
            mainModel.postValue(jsonStringToMainModel())
            BufferedWriter(FileWriter(getCategoriesFile())).also { writer ->
                writer.write(categoriesJson.toString())
                writer.close()
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun close() {
        //no-op
    }

    private fun getCategoriesFile(): File {
        return File(Environment.getDataDirectory().absolutePath +
                    "/data/com.stradivarius.charades/" + CATEGORIES_FILE)
    }

    private fun jsonStringToMainModel(): MainModel {
        val pairList: MutableList<Pair<String, List<String>>> = mutableListOf()
        val categoriesJSONArray = categoriesJson.getJSONArray(KEY_CATEGORIES)
        for (i in 0 until categoriesJSONArray.length()) {
            val category = categoriesJSONArray.getJSONObject(i)
            pairList.add(
                Pair(category.optString(KEY_CATEGORY),
                    category.optJSONArray(KEY_LIST).toList())
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

}