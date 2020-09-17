package com.stradivarius.charades.data.repository.local

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Local
import com.stradivarius.charades.data.model.MainModel

interface LocalStorage : Local {

    var isDirty: Boolean

    fun getCategories(): LiveData<MainModel>

    fun setCategories(list: List<Pair<String, List<String>>>)

    fun addCategory(title: String, list: String): Boolean

    fun writeCategoriesStateToJson()

}