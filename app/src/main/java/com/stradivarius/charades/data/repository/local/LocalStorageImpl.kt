package com.stradivarius.charades.data.repository.local

import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stradivarius.charades.data.dto.MainDto
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

    override var isDirty: Boolean = false

    private var mainDto = MainDto()
    private val mainModel = MutableLiveData<MainModel>()
    private var tempCategoryList: List<Pair<String, List<String>>> = listOf()

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
        mainDto = MainDto(JSONObject(jsonString))
        mainModel.postValue(mainDto.toModel())
        return mainModel
    }

    override fun setCategories(list: List<Pair<String, List<String>>>) {
        tempCategoryList = list
    }

    override fun addCategory(title: String, list: String): Boolean {
        try {
            JSONObject().also { jsonObject ->
                jsonObject.put(KEY_CATEGORY, title)
                jsonObject.put(KEY_LIST, JSONArray(list.split(',').toTypedArray()))
                mainDto.categoriesJson.getJSONArray(KEY_CATEGORIES).put(jsonObject)
            }
            mainModel.postValue(mainDto.toModel())
            writeJsonToFile(mainDto.categoriesJson)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun close() {
        //no-op
    }

    override fun writeCategoriesStateToJson() {
        if (tempCategoryList.isNotEmpty() && isDirty) {
            val jsonArray = JSONArray()
            for ((title, items) in tempCategoryList) {
                val jsonObject = JSONObject().apply {
                    put(KEY_CATEGORY, title)
                    put(KEY_LIST, items)
                }
                jsonArray.put(jsonObject)
            }
            val jsonObject = JSONObject().apply {
                put(KEY_CATEGORIES, jsonArray)
            }
            writeJsonToFile(jsonObject)
            tempCategoryList = listOf()
            isDirty = false
        }
    }

    private fun writeJsonToFile(json: JSONObject) {
        BufferedWriter(FileWriter(getCategoriesFile())).also { writer ->
            writer.write(json.toString())
            writer.close()
        }
    }

    private fun getCategoriesFile(): File {
        return File(Environment.getDataDirectory().absolutePath +
                "/data/com.stradivarius.charades/" + CATEGORIES_FILE)
    }

}