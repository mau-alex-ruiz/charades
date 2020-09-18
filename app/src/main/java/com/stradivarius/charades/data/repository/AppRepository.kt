package com.stradivarius.charades.data.repository

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Repository
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.ui.common.RepoStatus

interface AppRepository : Repository {

    var isDirty: Boolean

    fun getCategories(): LiveData<MainModel>

    fun addCategory(title: String, list: String): RepoStatus<Unit>

    fun editCategory(originalTitle: String, newTitle: String, list: String): RepoStatus<Unit>

    fun setCategories(list: List<Pair<String, List<String>>>)

    fun storeCategoriesState()

}