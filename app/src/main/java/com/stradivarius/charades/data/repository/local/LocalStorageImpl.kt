package com.stradivarius.charades.data.repository.local

import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stradivarius.charades.data.dto.MainDto
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.ui.common.RepoStatus
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


class LocalStorageImpl(
    private val assetManager: AssetManager
) : LocalStorage {

    companion object {
        const val CATEGORIES_FILE = "categories.json"
    }

    override var isDirty: Boolean = false

    private var mainDto = MainDto()
    private val mainModel = MutableLiveData<MainModel>()
    private var tempCategoryList: List<Pair<String, List<String>>> = listOf()
    private val categoriesFile: File by lazy {
        File(Environment.getDataDirectory().absolutePath +
                "/data/com.stradivarius.charades/" + CATEGORIES_FILE)
    }

    override fun getCategories(): LiveData<MainModel> {
        var jsonString = ""
        if (categoriesFile.length().toInt() == 0) {
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
            writeJsonArrayToFile(JSONArray(jsonString))
        } else {
            val fileReader = FileReader(categoriesFile)
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
        mainDto = MainDto(JSONArray(jsonString))
        mainModel.postValue(MainModel(mainDto))
        return mainModel
    }

    override fun setCategories(list: List<Pair<String, List<String>>>) {
        tempCategoryList = list
    }

    override fun addCategory(title: String, list: String): RepoStatus<Unit> {
        return try {
            mainDto.categoriesMap[title] = list.formatListForJson(',')
            mainModel.postValue(MainModel(mainDto))
            writeJsonArrayToFile(mainDto.toJsonArray())
            RepoStatus.Success()
        } catch (e: Exception) {
            RepoStatus.Error()
        }
    }

    override fun editCategory(originalTitle: String,
                              newTitle: String,
                              list: String): RepoStatus<Unit> {
        val newMap = linkedMapOf<String, List<String>>()
        for ((title, currList) in mainDto.categoriesMap.toList()) {
            if (title == originalTitle) {
                newMap[newTitle] = list.formatListForJson('\n')
            } else {
                newMap[title] = currList
            }
        }
        mainDto = MainDto(newMap)
        mainModel.postValue(MainModel(mainDto))
        writeJsonArrayToFile(mainDto.toJsonArray())
        return RepoStatus.Success()
    }

    override fun writeCategoriesStateToJson() {
        if (tempCategoryList.isNotEmpty() && isDirty) {
            val newMap = linkedMapOf<String, List<String>>()
            for ((title, list) in tempCategoryList) {
                newMap[title] = list
            }
            mainDto = MainDto(newMap)
            mainModel.postValue(MainModel(mainDto))
            writeJsonArrayToFile(mainDto.toJsonArray())
            tempCategoryList = listOf()
            isDirty = false
        }
    }

    override fun close() {
        //no-op
    }

    private fun writeJsonArrayToFile(json: JSONArray) {
        BufferedWriter(FileWriter(categoriesFile)).also { writer ->
            writer.write(json.toString())
            writer.close()
        }
    }

    private fun String.formatListForJson(delimiter: Char): List<String> {
        return split(delimiter).map { it.trim() }.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) {it})
    }

}