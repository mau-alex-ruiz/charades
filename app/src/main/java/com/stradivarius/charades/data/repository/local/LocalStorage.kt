package com.stradivarius.charades.data.repository.local

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Local
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.ui.common.RepoStatus

interface LocalStorage : Local {

    var isDirty: Boolean

    fun getCategories(): LiveData<MainModel>

    fun setCategories(list: List<Pair<String, List<String>>>)

    fun addCategory(title: String, list: String): RepoStatus<Unit>

    fun editCategory(title: String, list: String): RepoStatus<Unit>

    fun writeCategoriesStateToJson()

}